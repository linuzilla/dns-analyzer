package ncu.cc.digger.controllers;

import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.repositories.UnivReportViewRepository;
import ncu.cc.digger.services.CountryCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Routes.STATISTICS + Routes.STATISTICS_UNIVERSITIES)
public class UniversitiesController {
    private final UnivReportViewRepository univReportViewRepository;
    private final CountryCodeService countryCodeService;

    public UniversitiesController(UnivReportViewRepository univReportViewRepository, CountryCodeService countryCodeService) {
        this.univReportViewRepository = univReportViewRepository;
        this.countryCodeService = countryCodeService;
    }

    @GetMapping
    @MenuItem(value = MenuPaths.STATISTICS_UNIV, order = 2)
    public String index() {
        return Views.STATISTICS_UNIV;
    }

    @GetMapping("/{code}")
    public String univ(@PathVariable("code") String code, Model model, RedirectAttributes redirectAttributes) {
        String countryCode = code.toLowerCase().trim();
        String countryName = countryCodeService.getCountryByCode(countryCode);

        if (countryName == null) {
            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE, "country not found");
            return Routes.redirect(Routes.STATISTICS + Routes.STATISTICS_UNIVERSITIES);
        } else {
            model.addAttribute(FormVariables.COUNTRY_NAME, countryName);
            model.addAttribute(FormVariables.COUNTRY_CODE, countryCode);
            return Views.STATISTICS_UNIV;
        }

    }


}