package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.FormVariables;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import ncu.cc.digger.entities.UnivReportViewEntity;
import ncu.cc.digger.repositories.UnivReportViewRepository;
import ncu.cc.digger.services.CountryCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Routes.UNIV)
public class UnivController {
    private final UnivReportViewRepository univReportViewRepository;
    private final CountryCodeService countryCodeService;

    public UnivController(UnivReportViewRepository univReportViewRepository, CountryCodeService countryCodeService) {
        this.univReportViewRepository = univReportViewRepository;
        this.countryCodeService = countryCodeService;
    }

    @GetMapping(Routes.UNIV_BROWSE)
    public String browseWorld() {
        return Views.UNIV_BROWSE;
    }

    @GetMapping(Routes.UNIV_BROWSE + "/{code}")
    public String browse(@PathVariable("code") String code, Model model, RedirectAttributes redirectAttributes) {
        String countryCode = code.toLowerCase().trim();
        String countryName = countryCodeService.getCountryByCode(countryCode);

        if (countryName == null) {
            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE, "country not found");
            return Routes.redirect(Routes.UNIV);
        } else {
            model.addAttribute(FormVariables.COUNTRY_NAME, countryName);
            model.addAttribute(FormVariables.COUNTRY_CODE, countryCode);
        }
        return Views.UNIV_BROWSE;
    }

    @GetMapping(Routes.UNIV_BACKEND + Routes.UNIV_BACKEND_BROWSE + "/{code}/{pageNo}")
    @ResponseBody
    public Page<UnivReportViewEntity> showUnivPage(@PathVariable("code") String code, @PathVariable("pageNo") Integer pageNo) {
        String countryCode = code.toLowerCase().trim();
        String countryName = countryCodeService.getCountryByCode(countryCode);

        PageRequest pageable = PageRequest.of(pageNo, Constants.DEFAULT_PAGE_SIZE);

        if (countryName == null) {
            return Page.empty();
        } else {
            return this.univReportViewRepository.findByCountryCodeOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(countryCode, pageable);
        }
    }

    @GetMapping(Routes.UNIV_BACKEND + Routes.UNIV_BACKEND_BROWSE + "/{pageNo}")
    @ResponseBody
    public Page<UnivReportViewEntity> showAllPage(@PathVariable("pageNo") Integer pageNo) {
        PageRequest pageable = PageRequest.of(pageNo, Constants.DEFAULT_PAGE_SIZE);

        return this.univReportViewRepository.findAllByOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(pageable);
    }
}
