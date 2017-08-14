package fr.hardis.baz.demo.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fr.hardis.baz.demo.validation.Monnaie;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;
import java.math.BigDecimal;
import java.util.Locale;

public class MonnaieValidator implements ConstraintValidator<Monnaie, Double> {

    @Override
    public void initialize(Monnaie currency) {

    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        BigDecimalValidator validator = CurrencyValidator.getInstance();

        BigDecimal amount = validator.validate(String.valueOf(value), Locale.FRENCH);

        return ( null != amount);

    }
}
