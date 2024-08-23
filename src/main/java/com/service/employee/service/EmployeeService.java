package com.service.employee.service;

import com.service.employee.component.JsonComponent;
import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.EmployeeDto;
import com.service.employee.dto.ReadEmployeesFilterDto;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.exception.EmployeeNotCreatedException;
import com.service.employee.mapper.EmployeeMapper;
import com.service.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import static com.service.employee.constant.ApplicationErrorCodeConstant.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.service.employee.constant.ApplicationErrorMessageConstant.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final JsonComponent jsonComponent;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> readEmployees(final ReadEmployeesFilterDto readEmployeesFilterDto) {
        log.debug("EmployeeService#readEmployees: start: " + jsonComponent.convertObjectToJsonString(readEmployeesFilterDto));

        final PageRequest pageRequest = PageRequest.of(
                readEmployeesFilterDto.getPage(),
                readEmployeesFilterDto.getLimit(),
                Sort.by(Sort.Direction.ASC, "id")
        );

        final List<EmployeeEntity> employeeEntities;
        if (ObjectUtils.isNotEmpty(readEmployeesFilterDto.getEmployedAtYear()) && ObjectUtils.isNotEmpty(readEmployeesFilterDto.getDepartment())) {
            final Date dateFrom = getDateFrom(readEmployeesFilterDto.getEmployedAtYear());
            final Date dateTo = getDateTo(readEmployeesFilterDto.getEmployedAtYear());

            employeeEntities = employeeRepository.findAllByEmployedAtIsBetweenAndDepartment(
                    dateFrom,
                    dateTo,
                    readEmployeesFilterDto.getDepartment(),
                    pageRequest
            );
        } else if (ObjectUtils.isNotEmpty(readEmployeesFilterDto.getEmployedAtYear()) && ObjectUtils.isEmpty(readEmployeesFilterDto.getDepartment())) {
            final Date dateFrom = getDateFrom(readEmployeesFilterDto.getEmployedAtYear());
            final Date dateTo = getDateTo(readEmployeesFilterDto.getEmployedAtYear());

            employeeEntities = employeeRepository.findAllByEmployedAtIsBetween(
                    dateFrom,
                    dateTo,
                    pageRequest
            );
        } else if (ObjectUtils.isEmpty(readEmployeesFilterDto.getEmployedAtYear()) && ObjectUtils.isNotEmpty(readEmployeesFilterDto.getDepartment())) {
            employeeEntities = employeeRepository.findByDepartment(
                    readEmployeesFilterDto.getDepartment(),
                    pageRequest
            );
        } else {
            final Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageRequest);

            employeeEntities = employeeEntityPage.stream().toList();
        }

        final List<EmployeeDto> employeeDtos = employeeEntities.stream()
                .map(employeeMapper::toDto)
                .toList();

        log.debug("EmployeeService#readEmployees: end: " + jsonComponent.convertObjectToJsonString(employeeDtos));
        return employeeDtos;
    }

    public EmployeeDto createEmployee(final CreateEmployeeRequestDto createEmployeeRequestDto) {
        log.debug("EmployeeService#createEmployee: start: " + jsonComponent.convertObjectToJsonString(createEmployeeRequestDto));

        final EmployeeEntity employeeEntity = employeeMapper.fromDto(createEmployeeRequestDto);
        employeeEntity.setEmployedAt(Date.from(Instant.now()));

        final EmployeeEntity savedEmployeeEntity = saveEmployee(employeeEntity);
        final EmployeeDto employeeDto = employeeMapper.toDto(savedEmployeeEntity);

        log.debug("EmployeeService#createEmployee: end: " + jsonComponent.convertObjectToJsonString(employeeDto));
        return employeeDto;
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

    private Date getDateFrom(final int year) {
        final LocalDate localDate = LocalDate.now()
                .withYear(year)
                .with(TemporalAdjusters.firstDayOfYear());

        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }

    private Date getDateTo(final int year) {
        final LocalDate localDate = LocalDate.now()
                .withYear(year)
                .with(TemporalAdjusters.lastDayOfYear());

        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
