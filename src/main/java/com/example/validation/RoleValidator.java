package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    private List<String> validRoles;
    @Override
    public void initialize(ValidRole constraintAnnotation) {
        validRoles = Arrays.asList("Developer", "Tester", "Manager");
    }
    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        return validRoles.stream().anyMatch(validRole -> validRole.equalsIgnoreCase(role));
    }
}
