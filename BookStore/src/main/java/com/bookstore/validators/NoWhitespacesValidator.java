package com.bookstore.validators;

import com.bookstore.constraintvalidation.NoWhitespaces;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoWhitespacesValidator implements ConstraintValidator<NoWhitespaces, Object> {
    @Override
    public void initialize(NoWhitespaces constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof String value) {

            return !value.trim().isEmpty();
        }
        return true;
    }

}
