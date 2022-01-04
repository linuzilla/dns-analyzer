package ncu.cc.commons.webdev.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface MenuItem {
    String value() default "";
    String messageKey() default "";
    int	order() default -1;
    String[]	authorities() default "";
    String[]    negativeAuthorities() default  "";
}
