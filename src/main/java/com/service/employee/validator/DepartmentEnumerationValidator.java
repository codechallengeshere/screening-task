package com.service.employee.validator;

import com.service.employee.exception.DepartmentNotValidException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class DepartmentEnumerationValidator implements ConstraintValidator<com.service.employee.validator.DepartmentEnumeration, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            throw new DepartmentNotValidException();
        }

        try {
            com.service.employee.enumeration.DepartmentEnumeration.valueOf(value);
        } catch (final IllegalArgumentException illegalArgumentException) {
            throw new DepartmentNotValidException();
        }

        return true;
    }
}
