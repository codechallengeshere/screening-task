package com.service.employee.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.employee.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.service.employee.constant.ApplicationErrorCodeConstant.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.service.employee.constant.ApplicationErrorMessageConstant.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonComponent {

    private final ObjectMapper objectMapper;

    public String convertObjectToJsonString(final Object object) {
        log.trace("ApplicationHelper#convertObjectToJsonString: start");

        final String result;

        try {
            result = objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException jsonProcessingException) {
            throw new ApplicationException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        log.trace("ApplicationHelper#convertObjectToJsonString: end");
        return result;
    }
}
