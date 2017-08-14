package fr.hardis.baz.demo.validation.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import fr.hardis.baz.demo.enums.ColorEnum;
import fr.hardis.baz.demo.validation.Color;

public class ColorValidator implements ConstraintValidator<Color, String> {
    @Override
    public void initialize(Color color) {

    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
    	String colors = Arrays.asList(ColorEnum.values()).stream().map(x -> x.toString()).collect(Collectors.joining(" , "));
    	return StringUtils.contains(colors, string);

    }
}