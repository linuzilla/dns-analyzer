package ncu.cc.commons.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ZoneOrURLValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ZoneOrURL {
    String message() default "{message.validator.zone-not-exists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
