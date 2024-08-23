package com.service.employee.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationErrorCode {

    public static final String ERROR_CODE__INTERNAL_SERVER_ERROR = "0100";
    public static final String ERROR_CODE__VALIDATION_ERROR = "0200";
    public static final String ERROR_CODE__DEPARTMENT_NOT_VALID_ERROR = "0300";
    public static final String ERROR_CODE__EMPLOYEE_NOT_FOUND = "0400";
}
