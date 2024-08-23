package com.service.employee.unit.service;

import com.service.employee.dto.EmployeeDto;
import com.service.employee.dto.ReadEmployeesFilterDto;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.service.EmployeeService;
import com.service.employee.unit.EmployeeApplicationUnitTest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class EmployeeEntityService_readEmployeesTest extends EmployeeApplicationUnitTest {

    private EmployeeService employeeService;

    @Override
    @BeforeEach
    protected void beforeEach() {
        super.beforeEach();

        this.employeeService = new EmployeeService(
                jsonComponent,
                entityMapper,
                employeeRepositoryMock
        );
    }

    @Test
    void success_noDepartment_noEmployedAtYear() throws Exception {
        final DepartmentEnumeration[] departmentEnumerations = DepartmentEnumeration.values();
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i <= capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumerations[RandomUtils.nextInt(0, departmentEnumerations.length)])
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMock)
                .findAll(any(PageRequest.class));

        final ReadEmployeesFilterDto readEmployeesFilterDto = ReadEmployeesFilterDto.builder()
                .limit(capacity)
                .page(1)
                .build();

        final List<EmployeeDto> employeeDtos = employeeService.readEmployees(readEmployeesFilterDto);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(employeeDtos));
    }

    @Test
    void success_noDepartment_byEmployedAtYear() throws Exception {
        final DepartmentEnumeration[] departmentEnumerations = DepartmentEnumeration.values();
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i <= capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumerations[RandomUtils.nextInt(0, departmentEnumerations.length)])
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMock)
                .findAllByEmployedAtIsBetween(any(Date.class), any(Date.class), any(PageRequest.class));

        final ReadEmployeesFilterDto readEmployeesFilterDto = ReadEmployeesFilterDto.builder()
                .limit(capacity)
                .page(1)
                .employedAtYear(2024)
                .build();

        final List<EmployeeDto> employeeDtos = employeeService.readEmployees(readEmployeesFilterDto);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(employeeDtos));
    }

    @Test
    void success_byDepartment_noEmployedAtYear() throws Exception {
        final DepartmentEnumeration departmentEnumeration = DepartmentEnumeration.DIGITAL;
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i <= capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumeration)
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMock)
                .findAllByDepartment(any(DepartmentEnumeration.class), any(PageRequest.class));

        final ReadEmployeesFilterDto readEmployeesFilterDto = ReadEmployeesFilterDto.builder()
                .limit(capacity)
                .page(1)
                .department(departmentEnumeration)
                .build();

        final List<EmployeeDto> employeeDtos = employeeService.readEmployees(readEmployeesFilterDto);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(employeeDtos));
    }

    @Test
    void success_byDepartment_byEmployedAtYear() throws Exception {
        final DepartmentEnumeration departmentEnumeration = DepartmentEnumeration.DIGITAL;
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i <= capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumeration)
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMock)
                .findAllByEmployedAtIsBetweenAndDepartment(any(Date.class), any(Date.class), any(DepartmentEnumeration.class), any(PageRequest.class));

        final ReadEmployeesFilterDto readEmployeesFilterDto = ReadEmployeesFilterDto.builder()
                .limit(capacity)
                .page(1)
                .department(departmentEnumeration)
                .employedAtYear(2024)
                .build();

        final List<EmployeeDto> employeeDtos = employeeService.readEmployees(readEmployeesFilterDto);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(employeeDtos));
    }

    private Date getDateFrom() {
        final LocalDate localDate = LocalDate.now().minusYears(3);
        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }

    private Date getDateTo() {
        final LocalDate localDate = LocalDate.now().plusYears(3);
        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
