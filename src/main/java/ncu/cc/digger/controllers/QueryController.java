package ncu.cc.digger.controllers;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.commons.validators.ZoneOrURLValidator;
import ncu.cc.commons.validators.ZoneValidValidator;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.controllers.base.WithCaptcha;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.formbeans.QueryZoneForm;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.DigWithCacheService;
import ncu.cc.digger.services.ZoneQueryService;
import ncu.cc.digger.services.ZoneQueryWithCacheService;
import ncu.cc.digger.sessionbeans.AnonymousUserSession;
import ncu.cc.digger.utils.DNSUtil;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Matcher;

@Controller
@RequestMapping(Routes.QUERY)
public class QueryController extends WithCaptcha {
    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    private final ReportRepository reportRepository;
    private final ZoneQueryService zoneQueryService;
    private final AuthenticationService authenticationService;
    private final DigWithCacheService digWithCacheService;
    private final DiggerBackendWorker diggerBackendWorker;
    private final ZoneQueryWithCacheService zoneQueryWithCacheService;
    @Resource(name = BeanIds.ANONYMOUS_USER_SESSION_BEAN)
    private AnonymousUserSession anonymousUserSession;

    public QueryController(
            ReportRepository reportRepository,
            ZoneQueryService zoneQueryService,
            AuthenticationService authenticationService,
            DigWithCacheService digWithCacheService,
            DiggerBackendWorker diggerBackendWorker,
            ZoneQueryWithCacheService zoneQueryWithCacheService) {
        this.reportRepository = reportRepository;
        this.zoneQueryService = zoneQueryService;
        this.authenticationService = authenticationService;
        this.digWithCacheService = digWithCacheService;
        this.diggerBackendWorker = diggerBackendWorker;
        this.zoneQueryWithCacheService = zoneQueryWithCacheService;
    }

    @GetMapping
    public String queryWithCaptcha(Model model) {
        if (authenticationService.hasAnyRole(RolesEnum.ROLE_SYSOP, RolesEnum.ROLE_ADMIN)) {
            addRecaptchaVariable(model);
        }

        QueryZoneForm queryZoneForm = new QueryZoneForm();
        queryZoneForm.setZone(anonymousUserSession.getQueryZone());
        model.addAttribute(FormVariables.ZONE_QUERY_FORM, queryZoneForm);

        return Views.QUERY_INDEX;
    }

    @PostMapping
    public String zoneQuery(
            @Valid @ModelAttribute(FormVariables.ZONE_QUERY_FORM) QueryZoneForm form,
            BindingResult result,
            @RequestParam(value = Constants.G_RECAPTCHA_RESPONSE, required = false) String recaptchaResponse,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            return Views.QUERY_INDEX;
        }

        if (diggerBackendWorker.getNumberOfEvents() >= Constants.MAX_QUEUED_REQUEST) {
            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE,
                    "Too many request waiting to be served, wait a moment and try again!");
            return Routes.redirect(Routes.ROOT);
        }

//        StackTraceUtil.print1(form);
        boolean hasCaptcha = false;

        if (authenticationService.hasAnyRole(RolesEnum.ROLE_SYSOP, RolesEnum.ROLE_ADMIN)) {
            hasCaptcha = true;
        } else {
            if (StringUtil.isNotEmpty(recaptchaResponse)) {
                try {
                    if (verifyCaptcha(recaptchaResponse, authenticationService.getRemoteAddr()).block()) {
                        hasCaptcha = true;
                    }
                } catch (Exception e) {
                    logger.info("exception: {}", e.getMessage());
                }
            }
        }

        Optional<String> zoneOptional = DNSUtil.verifyOrRetrieveZone(form.getZone());

        if (zoneOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE, "Incorrect input");
            return Routes.redirect(Routes.ROOT);
        }

        String zone = zoneOptional.get();

        if (zone.endsWith(Constants.REVERSE_IPV6)) {
            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE, "Reverse IPv6 not support");
            return Routes.redirect(Routes.ROOT);
        }

        if (!zone.equals(anonymousUserSession.getQueryZone())) {
            anonymousUserSession.setQueryZone(zone);
            anonymousUserSession.setZoneStatusEnum(AnonymousUserSession.ZoneStatusEnum.UNKNOWN);
        }

        Optional<ReportEntity> optional = reportRepository.findById(zone);

        if (optional.isPresent() && anonymousUserSession.getZoneStatusEnum() != AnonymousUserSession.ZoneStatusEnum.RELOAD) {
            anonymousUserSession.setZoneStatusEnum(AnonymousUserSession.ZoneStatusEnum.IN_DB);
            return Routes.redirect(Routes.ZONE + "/" + anonymousUserSession.getQueryZone());
        } else if (hasCaptcha) {
            var bestFit = this.digWithCacheService.findBelongingZone(zone).block();

            if (StringUtil.isNotEmpty(bestFit)) {
                var foundDomain = DNSUtil.stripTrailingDot(bestFit);

                if (foundDomain.equalsIgnoreCase(zone)) {
                    logger.info("domain {} found", foundDomain);
                    boolean isReload = anonymousUserSession.getZoneStatusEnum() == AnonymousUserSession.ZoneStatusEnum.RELOAD;

                    if (isReload && optional.isPresent()) {
                        reportRepository.delete(optional.get());
                    }

                    anonymousUserSession.setZoneStatusEnum(AnonymousUserSession.ZoneStatusEnum.WAITING);
                    this.zoneQueryService.triggerQueryZone(foundDomain,
                            authenticationService.getRemoteAddr(),
                            isReload);
                    return Routes.redirect(Routes.ZONE + "/" + anonymousUserSession.getQueryZone());
                } else {
                    logger.info("domain {} belongs to {}", zone, foundDomain);
                    redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE,
                            String.format("<b>%s</b> is not a zone, try <b>%s</b>", zone, foundDomain));

                    anonymousUserSession.setQueryZone(DNSUtil.stripTrailingDot(foundDomain));
                    anonymousUserSession.setZoneStatusEnum(AnonymousUserSession.ZoneStatusEnum.UNKNOWN);

                    return Routes.redirect(Routes.ROOT);
                }
            } else {
                redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE,
                        String.format("Zone: <b>%s</b>, NOT Found, ", zone));
                return Routes.redirect(Routes.ROOT);
            }
        }

        addRecaptchaVariable(model);

        model.addAttribute(FormVariables.ZONE_QUERY_FORM, form);
        return Views.QUERY_INDEX;
    }

    @GetMapping(Routes.QUERY_RELOAD + "/" + Constants.ZONE_PATH_VARIABLE)
    public String reload(@PathVariable("zone") String zone, Model model) {
        if (!authenticationService.hasAnyRole(RolesEnum.ROLE_SYSOP, RolesEnum.ROLE_ADMIN)) {
            addRecaptchaVariable(model);
        }

        anonymousUserSession.setQueryZone(DNSUtil.stripTrailingDot(zone));
        anonymousUserSession.setZoneStatusEnum(AnonymousUserSession.ZoneStatusEnum.RELOAD);

        QueryZoneForm queryZoneForm = new QueryZoneForm();
        queryZoneForm.setZone(anonymousUserSession.getQueryZone());
        model.addAttribute(FormVariables.ZONE_QUERY_FORM, queryZoneForm);

        return Views.QUERY_INDEX;
    }

}
