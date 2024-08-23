package com.service.employee.handler;

import com.service.employee.component.JsonComponent;
import com.service.employee.constant.ApplicationErrorCodeConstant;
import com.service.employee.constant.ApplicationErrorMessageConstant;
import com.service.employee.dto.ApiErrorResponseDto;
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
    public ResponseEntity<ApiErrorResponseDto> handleEmployeeNotFoundException(final EmployeeNotFoundException employeeNotFoundException) {
        log.error("ApplicationExceptionHandler#handleEmployeeNotFoundException: start: " + jsonComponent.convertObjectToJsonString(employeeNotFoundException));

        final var httpStatus = employeeNotFoundException.getHttpStatus();

        final var apiErrorResponseDto = getApiErrorResponseDto(employeeNotFoundException);

        log.debug("ApplicationExceptionHandler#handleEmployeeNotFoundException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return new ResponseEntity<>(apiErrorResponseDto, httpStatus);
    }

    @ExceptionHandler(EmployeeNotCreatedException.class)
    public ResponseEntity<ApiErrorResponseDto> handleEmployeeNotCreatedException(final EmployeeNotCreatedException employeeNotCreatedException) {
        log.error("ResetPasswordController#handleEmployeeNotCreatedException: start: " + jsonComponent.convertObjectToJsonString(employeeNotCreatedException));

        final var httpStatus = employeeNotCreatedException.getHttpStatus();

        final var apiErrorResponseDto = getApiErrorResponseDto(employeeNotCreatedException);

        log.debug("ResetPasswordController#handleEmployeeNotCreatedException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return new ResponseEntity<>(apiErrorResponseDto, httpStatus);
    }

    @ExceptionHandler(DepartmentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleDepartmentNotValidException(final DepartmentNotValidException departmentNotValidException) {
        log.error("ApplicationExceptionHandler#handleDepartmentNotValidException: start: " + jsonComponent.convertObjectToJsonString(departmentNotValidException));

        final var httpStatus = HttpStatus.BAD_REQUEST;

        final var apiException = new ApiException(
                ApplicationErrorCodeConstant.ERROR_CODE__DEPARTMENT_NOT_VALID_ERROR,
                ApplicationErrorMessageConstant.ERROR_MESSAGE__DEPARTMENT_NOT_VALID,
                httpStatus
        );

        final var apiErrorResponseDto = getApiErrorResponseDto(apiException);

        log.debug("ApplicationExceptionHandler#handleIllegalArgumentException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return new ResponseEntity<>(apiErrorResponseDto, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiErrorResponseDto> handleHttpMessageNotReadableException() {
        log.error("ApplicationExceptionHandler#handleHttpMessageNotReadableException: start");

        final var httpStatus = HttpStatus.BAD_REQUEST;

        final var apiException = new ApiException(
                ApplicationErrorCodeConstant.ERROR_CODE__VALIDATION_ERROR,
                ApplicationErrorMessageConstant.ERROR_MESSAGE__REQUIRED_PARAMS_ERROR,
                httpStatus
        );

        final var apiErrorResponseDto = getApiErrorResponseDto(apiException);

        log.error("ApplicationExceptionHandler#handleHttpMessageNotReadableException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return new ResponseEntity<>(apiErrorResponseDto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponseDto> handleMethodArgumentNotValidException(
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
                ApplicationErrorCodeConstant.ERROR_CODE__VALIDATION_ERROR,
                jsonComponent.convertObjectToJsonString(errors),
                httpStatus
        );

        final var apiErrorResponseDto = getApiErrorResponseDto(apiException);

        log.debug("ApplicationExceptionHandler#handleMethodArgumentNotValidException: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return new ResponseEntity<>(apiErrorResponseDto, httpStatus);
    }

    private ApiErrorResponseDto getApiErrorResponseDto(
            final BaseException applicationException
    ) {
        log.trace("ApplicationExceptionHandler#getApiErrorResponseDto: start: " + jsonComponent.convertObjectToJsonString(applicationException));

        final var apiErrorResponseDto = ApiErrorResponseDto.builder()
                .errorCode(applicationException.getErrorCode())
                .errorMessage(applicationException.getErrorMessage())
                .statusCode(applicationException.getHttpStatus().value())
                .build();

        log.trace("ApplicationExceptionHandler#getApiErrorResponseDto: end: " + jsonComponent.convertObjectToJsonString(apiErrorResponseDto));
        return apiErrorResponseDto;
    }
}
