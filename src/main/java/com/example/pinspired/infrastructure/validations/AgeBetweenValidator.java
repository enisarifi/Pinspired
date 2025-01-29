package com.example.pinspired.infrastructure.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeBetweenValidator implements ConstraintValidator<AgeBetween, LocalDate> {

    private int minAge;
    private int maxAge;

    //initialize value from constraint
    @Override
    public void initialize(AgeBetween constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
        this.maxAge = constraintAnnotation.max();
    }


    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // Let other validators handle null values
        }

        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= minAge && age <= maxAge;
    }
}



