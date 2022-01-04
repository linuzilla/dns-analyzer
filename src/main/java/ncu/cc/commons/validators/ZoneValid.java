package ncu.cc.commons.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ZoneValidValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ZoneValid {
    String message() default "{message.validator.zone-not-exists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
