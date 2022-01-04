package ncu.cc.digger.controllers;

import ncu.cc.commons.utils.Pair;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.converters.HealthResultToEntity;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.entities.UniversitiesEntity;
import ncu.cc.digger.models.APICommonResponse;
import ncu.cc.digger.repositories.UniversityRepository;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.CountryCodeService;
import ncu.cc.digger.services.DNSHealthAnalyzeService;
import ncu.cc.digger.services.ReportService;
import ncu.cc.digger.sessionbeans.AnonymousUserSession;
import ncu.cc.digger.utils.DNSUtil;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(Routes.ZONE)
public class ZoneController {
    private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    private final ReportService reportService;
    private final DNSHealthAnalyzeService dnsHealthAnalyzeService;
    private final AuthenticationService authenticationService;
    private final DiggerBackendWorker diggerBackendWorker;
    private final CountryCodeService countryCodeService;
    private final UniversityRepository universityRepository;
    @Resource(name = BeanIds.ANONYMOUS_USER_SESSION_BEAN)
    private AnonymousUserSession anonymousUserSession;

    public ZoneController(ReportService reportService, DNSHealthAnalyzeService dnsHealthAnalyzeService, AuthenticationService authenticationService, DiggerBackendWorker diggerBackendWorker, CountryCodeService countryCodeService, UniversityRepository universityRepository) {
        this.reportService = reportService;
        this.dnsHealthAnalyzeService = dnsHealthAnalyzeService;
        this.authenticationService = authenticationService;
        this.diggerBackendWorker = diggerBackendWorker;
        this.countryCodeService = countryCodeService;
        this.universityRepository = universityRepository;
    }

    @GetMapping("/" + Constants.ZONE_PATH_VARIABLE)
    public String index(@PathVariable("zone") String zone, Model model) {
        var z = DNSUtil.stripTrailingDot(zone);
        var countryCode = DNSUtil.retrieveCountryCode(z);
        var countryName = countryCodeService.getCountryByCode(countryCode);

        if (countryName != null) {
            model.addAttribute(FormVariables.COUNTRY_NAME, countryName);
            model.addAttribute(FormVariables.COUNTRY_CODE, countryCode);

            universityRepository.findById(z).ifPresent(universitiesEntity ->
                model.addAttribute(FormVariables.UNIVERSITY_NAME, universitiesEntity.getZoneName())
            );
        }

        model.addAttribute(FormVariables.ZONE, z);
        return Views.ZONE;
    }

    @GetMapping("/" + Constants.ZONE_PATH_VARIABLE + "/backend")
    @ResponseBody
    public APICommonResponse zoneBackend(@PathVariable("zone") String queryZone) {
        var zone = DNSUtil.stripTrailingDot(queryZone);

        APICommonResponse apiCommonResponse = new APICommonResponse();

        Optional<ReportEntity> optional = this.reportService.findByZoneId(zone);

        if (optional.isPresent()) {
            logger.info("query {} (from: {})", zone, authenticationService.getRemoteAddr());
            apiCommonResponse.setStatus(APICommonResponse.Status.success);

            apiCommonResponse.setData(new Pair<>(
                    optional.get(),
                    dnsHealthAnalyzeService.problemsAndSuggestions(
                            HealthResultToEntity.reportToHealthResult(optional.get())
                    )));
        } else {
            if (zone.equals(anonymousUserSession.getQueryZone())
                    && anonymousUserSession.getZoneStatusEnum() == AnonymousUserSession.ZoneStatusEnum.WAITING) {

                if (diggerBackendWorker.getNumberOfEvents() > 0) {
                    apiCommonResponse.setStatus(APICommonResponse.Status.processing);
                } else {
                    apiCommonResponse.setStatus(APICommonResponse.Status.failed);
                }
//                logger.info("not found but processing {}", zone);
            } else {
                apiCommonResponse.setStatus(APICommonResponse.Status.failed);
                logger.info("not found {}", zone);
            }
        }

        return apiCommonResponse;
        // return this.reportService.findByZoneId(zoneName).orElse(new ReportEntity());
    }
}
