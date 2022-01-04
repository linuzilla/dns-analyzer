package ncu.cc.digger.services;

import ncu.cc.commons.lookup.ObjectLookup;
import ncu.cc.commons.lookup.ObjectLookupImpl;
import ncu.cc.commons.lookup.ObjectStored;
import ncu.cc.commons.utils.ReadfileUtil;
import ncu.cc.digger.constants.ValueConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class CountryCodeServiceImpl implements CountryCodeService {
    private static final int MAX_MATCHES = 20;
    public static class CountryCodeModel {
        private Map<String,String> countries;

        public Map<String, String> getCountries() {
            return countries;
        }

        public void setCountries(Map<String, String> countries) {
            this.countries = countries;
        }
    }
    private final Map<String,String> countries;
    private final ObjectLookup<CountryCodeEntry> finder;

    public CountryCodeServiceImpl(@Value(ValueConstants.COUNTRY_CODES) String yamlFile) throws FileNotFoundException {
        var yaml = new Yaml(new Constructor(CountryCodeModel.class));

        CountryCodeModel countryCodeModel = yaml.load(ReadfileUtil.readFrom(yamlFile));

        countries = countryCodeModel.getCountries();

        finder = new ObjectLookupImpl<>();

        countries.forEach((code, name) -> {
            CountryCodeEntry entry = new CountryCodeEntry(code, name, String.format("%s (.%s)", name, code));
            finder.add(name, entry);
            finder.add(code, entry);
            finder.add("." + code, entry);
        });
    }

    @Override
    public String getCountryByCode(String code) {
        return countries.get(code);
    }

    @Override
    public Map<String, String> getCountries() {
        return countries;
    }

    @Override
    public List<ObjectStored<CountryCodeEntry>> findMatched(String input) {
        return this.finder.findMatched(input, MAX_MATCHES);
    }
}
