package com.service.employee.service;

import com.service.employee.component.JsonComponent;
import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.CreateEmployeeResponseDto;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.exception.EmployeeNotCreatedException;
import com.service.employee.mapper.EmployeeMapper;
import com.service.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

import static com.service.employee.constant.ApplicationErrorCodeConstant.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.service.employee.constant.ApplicationErrorMessageConstant.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final JsonComponent jsonComponent;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public CreateEmployeeResponseDto createEmployee(final CreateEmployeeRequestDto createEmployeeRequestDto) {
        log.debug("EmployeeService#createEmployee: start: " + jsonComponent.convertObjectToJsonString(createEmployeeRequestDto));

        final EmployeeEntity employeeEntity = employeeMapper.fromDto(createEmployeeRequestDto);
        employeeEntity.setEmployedAt(Date.from(Instant.now()));

        final EmployeeEntity savedEmployeeEntity = saveEmployee(employeeEntity);
        final CreateEmployeeResponseDto createEmployeeResponseDto = employeeMapper.toDto(savedEmployeeEntity);

        log.debug("EmployeeService#createEmployee: end: " + jsonComponent.convertObjectToJsonString(createEmployeeResponseDto));
        return createEmployeeResponseDto;
    }

    private EmployeeEntity saveEmployee(final EmployeeEntity employeeEntity) {
        log.debug("EmployeeService#saveEmployee: start: " + jsonComponent.convertObjectToJsonString(employeeEntity));

        EmployeeEntity savedEmployeeEntity = null;
        try {
            savedEmployeeEntity = employeeRepository.save(employeeEntity);
        } catch (final Exception exception) {
            log.warn("EmployeeService#saveEmployee: Exception: " + exception);

            throw new EmployeeNotCreatedException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        log.debug("EmployeeService#saveEmployee: end: " + jsonComponent.convertObjectToJsonString(savedEmployeeEntity));
        return savedEmployeeEntity;
    }

    public void deleteEmployee(final long employeeId) {
        log.debug("EmployeeService#deleteEmployee: start: " + jsonComponent.convertObjectToJsonString(employeeId));

        employeeRepository.deleteById(employeeId);

        log.debug("EmployeeService#deleteEmployee: end");
    }
}
