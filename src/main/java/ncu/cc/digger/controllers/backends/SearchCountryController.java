package ncu.cc.digger.controllers.backends;

import ncu.cc.commons.lookup.ObjectStored;
import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.models.AutocompleteModel;
import ncu.cc.digger.services.CountryCodeService;
import ncu.cc.digger.utils.ApplyHighlightUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Routes.BACKEND)
public class SearchCountryController {
    private final CountryCodeService countryCodeService;

    public SearchCountryController(CountryCodeService countryCodeService) {
        this.countryCodeService = countryCodeService;
    }

    private AutocompleteModel toAutocompleteModel(ObjectStored<CountryCodeService.CountryCodeEntry> entry) {
        AutocompleteModel autocompleteModel = new AutocompleteModel();

        autocompleteModel.setId(entry.getObj().getCountryCode());
        autocompleteModel.setLabel(entry.getObj().getDisplayName());
        autocompleteModel.setValue(entry.getObj().getDisplayName());

        return autocompleteModel;
    }

    @GetMapping(Routes.BACKEND_COUNTRY)
    public List<AutocompleteModel> searchAutocomplete(@RequestParam("term") String term) {
        List<AutocompleteModel> list = this.countryCodeService.findMatched(term).stream()
                .map(this::toAutocompleteModel)
                .collect(Collectors.toList());

//        officialAccountService.findByAccountLike(term).forEach(officialAccount -> {
//            AutocompleteModel model = new AutocompleteModel();
//            model.setId(officialAccount.getGAccount());
//            model.setValue(officialAccount.getGAccount());
//            model.setLabel(String.format("%s  <small>[%s/%s] - %s</small>",
//                    officialAccount.getGAccount(),
//                    officialAccount.getUnitNo(), officialAccount.getTunitNo(),
//                    officialAccount.getManagerName()));
//
//            list.add(model);
//        });

        ApplyHighlightUtils.apply(list, term);
        return list;
    }
}
