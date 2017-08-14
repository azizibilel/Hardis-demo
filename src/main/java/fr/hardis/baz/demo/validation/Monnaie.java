package fr.hardis.baz.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import fr.hardis.baz.demo.validation.impl.MonnaieValidator;

@Constraint(validatedBy = MonnaieValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Monnaie {


    String message() default "Invalid amount/currency.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
