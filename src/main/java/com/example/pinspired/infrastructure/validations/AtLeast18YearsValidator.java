package com.example.pinspired.infrastructure.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AtLeast18YearsValidator implements ConstraintValidator<AtLeast18Years, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // Let other validators handle null values
        }

        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
    }
}