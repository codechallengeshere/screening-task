package com.service.employee.handler;

import com.service.employee.component.JsonComponent;
import com.service.employee.constant.ApplicationErrorCode;
import com.service.employee.constant.ApplicationErrorMessage;
import com.service.employee.dto.ApiErrorResponse;
import com.service.employee.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler {

    private final JsonComponent jsonComponent;

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotFoundException(final EmployeeNotFoundException employeeNotFoundException) {
        log.error("ApplicationExceptionHandler#handleEmployeeNotFoundException: start: " + jsonComponent.convertObjectToJsonString(employeeNotFoundException));

        final var httpStatus = employeeNotFoundException.getHttpStatus();

        final var apiErrorResponse = getApiErrorResponse(employeeNotFoundException);

        log.debug("ApplicationExceptionHandler#handleEmployeeNotFoundException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(EmployeeNotCreatedException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotCreatedException(final EmployeeNotCreatedException employeeNotCreatedException) {
        log.error("ResetPasswordController#handleEmployeeNotCreatedException: start: " + jsonComponent.convertObjectToJsonString(employeeNotCreatedException));

        final var httpStatus = employeeNotCreatedException.getHttpStatus();

        final var apiErrorResponse = getApiErrorResponse(employeeNotCreatedException);

        log.debug("ResetPasswordController#handleEmployeeNotCreatedException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(DepartmentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleDepartmentNotValidException(final DepartmentNotValidException departmentNotValidException) {
        log.error("ApplicationExceptionHandler#handleDepartmentNotValidException: start: " + jsonComponent.convertObjectToJsonString(departmentNotValidException));

        final var httpStatus = HttpStatus.BAD_REQUEST;

        final var apiException = new ApiException(
                ApplicationErrorCode.ERROR_CODE__DEPARTMENT_NOT_VALID_ERROR,
                ApplicationErrorMessage.ERROR_MESSAGE__DEPARTMENT_NOT_VALID,
                httpStatus
        );

        final var apiErrorResponse = getApiErrorResponse(apiException);

        log.debug("ApplicationExceptionHandler#handleIllegalArgumentException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error("ApplicationExceptionHandler#handleHttpMessageNotReadableException: start");

        final var httpStatus = HttpStatus.BAD_REQUEST;

        final var apiException = new ApiException(
                ApplicationErrorCode.ERROR_CODE__VALIDATION_ERROR,
                ApplicationErrorMessage.ERROR_MESSAGE__MISSING_REQUIRED_PARAMS,
                httpStatus
        );

        final var apiErrorResponse = getApiErrorResponse(apiException);

        log.debug("ApplicationExceptionHandler#handleHttpMessageNotReadableException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        final Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            final String fieldName = ((FieldError) error).getField();
                            final String errorMessage = error.getDefaultMessage();

                            errors.put(fieldName, errorMessage);
                        }
                );

        log.error("ApplicationExceptionHandler#handleMethodArgumentNotValidException: start: " + jsonComponent.convertObjectToJsonString(errors));

        final var httpStatus = HttpStatus.BAD_REQUEST;

        final var apiException = new ApiException(
                ApplicationErrorCode.ERROR_CODE__VALIDATION_ERROR,
                jsonComponent.convertObjectToJsonString(errors),
                httpStatus
        );

        final var apiErrorResponse = getApiErrorResponse(apiException);

        log.debug("ApplicationExceptionHandler#handleMethodArgumentNotValidException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    private ApiErrorResponse getApiErrorResponse(
            final BaseException applicationException
    ) {
        log.trace("ApplicationExceptionHandler#getApiErrorResponse: start: " + jsonComponent.convertObjectToJsonString(applicationException));

        final var apiErrorResponse = ApiErrorResponse.builder()
                .errorCode(applicationException.getErrorCode())
                .errorMessage(applicationException.getErrorMessage())
                .statusCode(applicationException.getHttpStatus().value())
                .build();

        log.trace("ApplicationExceptionHandler#getApiErrorResponse: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponse));
        return apiErrorResponse;
    }
}
