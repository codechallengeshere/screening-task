package com.service.employee.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNotCreatedException extends BaseException {

    public EmployeeNotCreatedException(final String errorCode, final String errorMessage, final HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}

