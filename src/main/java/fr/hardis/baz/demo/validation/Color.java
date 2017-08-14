package fr.hardis.baz.demo.validation;

import fr.hardis.baz.demo.validation.impl.ColorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ColorValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Color {


    String message() default "Incorrect value for color";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
