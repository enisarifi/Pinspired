package com.example.pinspired.infrastructure.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartsWithValidator implements ConstraintValidator<StartsWith, String> {

    private String value;
    private boolean ignoreCase;

    //initialize value from constraint
    @Override
    public void initialize(StartsWith constraintAnnotation) {
        this.value = constraintAnnotation.value();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }


    @Override
    public boolean isValid(String prefix, ConstraintValidatorContext context) {
        if (prefix == null) {
            return true; // Let other validators handle null values
        }

        return ignoreCase ? prefix.toLowerCase().startsWith(value.toLowerCase()) : prefix.startsWith(value);
    }
}



