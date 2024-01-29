package mio68.lab.spring.web.validation.constraint;

/* This is an example of composite constraint.
 * see https://www.baeldung.com/java-bean-validation-constraint-composition
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^[a-zA-Z]([a-zA-Z0-9_-])*$")
//@Length(min = 6, max = 32, message = "must have between 6 and 32 characters")
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface ValidFieldName {

    String message() default "field format error.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
