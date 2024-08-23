package com.service.employee.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends BaseException {

    public EmployeeNotFoundException(final String errorCode, final String errorMessage, final HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}

