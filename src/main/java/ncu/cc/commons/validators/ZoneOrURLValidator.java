package ncu.cc.commons.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ZoneOrURLValidator implements ConstraintValidator<ZoneOrURL,String> {
    public static final Pattern COMMON_URL_PATTERN = Pattern.compile("^\\s*(https?://)?([-a-z0-9]+(\\.[-a-z0-9]+)+)(/.*)?$");
    public static final int ZONE_GROUP_NUMBER = 2;

    @Override
    public boolean isValid(String zone, ConstraintValidatorContext constraintValidatorContext) {
        if (ZoneValidValidator.COMMON_DOMAIN_PATTERN.matcher(zone).matches()) {
            return true;
        } else {
            return COMMON_URL_PATTERN.matcher(zone.toLowerCase()).matches();
        }
    }
}
