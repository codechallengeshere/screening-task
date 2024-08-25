package com.service.employee.integration.controller;

import com.service.employee.constant.EmployeeControllerConstant;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.integration.EmployeeApplicationIntegrationTest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeController_readEmployeesIT extends EmployeeApplicationIntegrationTest {

    @Test
    void success_noDepartment_noEmployedAtYear() throws Exception {
        final DepartmentEnumeration[] departmentEnumerations = DepartmentEnumeration.values();
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumerations[RandomUtils.nextInt(0, departmentEnumerations.length)])
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMockBean)
                .findAll(any(PageRequest.class));

        final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("limit", String.valueOf(capacity));
        queryParams.add("page", "1");
        queryParams.add("department", null);
        queryParams.add("employed_at_year", null);

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.READ_EMPLOYEES_ENDPOINT_PATH;
        final var requestBuilder = get(url)
                .accept(MediaType.APPLICATION_JSON)
                .queryParams(queryParams);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void success_noDepartment_byEmployedAtYear() throws Exception {
        final DepartmentEnumeration[] departmentEnumerations = DepartmentEnumeration.values();
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumerations[RandomUtils.nextInt(0, departmentEnumerations.length)])
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMockBean)
                .findAllByEmployedAtIsBetween(any(Date.class), any(Date.class), any(PageRequest.class));

        final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("limit", String.valueOf(capacity));
        queryParams.add("page", "1");
        queryParams.add("department", null);
        queryParams.add("employed_at_year", "2024");

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.READ_EMPLOYEES_ENDPOINT_PATH;
        final var requestBuilder = get(url)
                .accept(MediaType.APPLICATION_JSON)
                .queryParams(queryParams);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void success_byDepartment_noEmployedAtYear() throws Exception {
        final DepartmentEnumeration departmentEnumeration = DepartmentEnumeration.DIGITAL;
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumeration)
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMockBean)
                .findAllByDepartment(any(DepartmentEnumeration.class), any(PageRequest.class));

        final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("limit", String.valueOf(capacity));
        queryParams.add("page", "1");
        queryParams.add("department", departmentEnumeration.getDepartment());
        queryParams.add("employed_at_year", null);

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.READ_EMPLOYEES_ENDPOINT_PATH;
        final var requestBuilder = get(url)
                .accept(MediaType.APPLICATION_JSON)
                .queryParams(queryParams);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void success_byDepartment_byEmployedAtYear() throws Exception {
        final DepartmentEnumeration departmentEnumeration = DepartmentEnumeration.DIGITAL;
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final int capacity = 10;
        final List<EmployeeEntity> employeeEntities = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumeration)
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        final PageImpl<EmployeeEntity> employeeEntitiesPage = new PageImpl<>(employeeEntities);

        doReturn(employeeEntitiesPage)
                .when(employeeRepositoryMockBean)
                .findAllByEmployedAtIsBetweenAndDepartment(any(Date.class), any(Date.class), any(DepartmentEnumeration.class), any(PageRequest.class));

        final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("limit", String.valueOf(capacity));
        queryParams.add("page", "1");
        queryParams.add("department", departmentEnumeration.getDepartment());
        queryParams.add("employed_at_year", "2024");

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.READ_EMPLOYEES_ENDPOINT_PATH;
        final var requestBuilder = get(url)
                .accept(MediaType.APPLICATION_JSON)
                .queryParams(queryParams);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
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
