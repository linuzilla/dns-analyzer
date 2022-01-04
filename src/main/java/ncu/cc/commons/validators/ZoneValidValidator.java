package ncu.cc.commons.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ZoneValidValidator implements ConstraintValidator<ZoneValid,String> {
    public static final Pattern COMMON_DOMAIN_PATTERN = Pattern.compile("^\\s*[-A-Za-z0-9]+(\\.[-A-Za-z0-9]+)+\\s*$");

    @Override
    public boolean isValid(String zone, ConstraintValidatorContext constraintValidatorContext) {
        return COMMON_DOMAIN_PATTERN.matcher(zone).matches();
    }
}
