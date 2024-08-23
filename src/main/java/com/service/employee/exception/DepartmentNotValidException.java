package com.service.employee.exception;

public class DepartmentNotValidException extends RuntimeException {

    public DepartmentNotValidException() {
        super();
    }

    public DepartmentNotValidException(final String message) {
        super(message);
    }
}

