package ncu.cc.digger.services;

import ncu.cc.commons.lookup.ObjectStored;

import java.util.List;
import java.util.Map;

public interface CountryCodeService {
    String getCountryByCode(String code);

    Map<String, String> getCountries();

    List<ObjectStored<CountryCodeEntry>> findMatched(String input);

    class CountryCodeEntry {
        private final String countryCode;
        private final String countryName;
        private final String displayName;

        public CountryCodeEntry(String countryCode, String countryName, String displayName) {
            this.countryCode = countryCode;
            this.countryName = countryName;
            this.displayName = displayName;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
