package com.service.employee.unit.service;

import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.CreateEmployeeResponseDto;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.service.EmployeeService;
import com.service.employee.unit.EmployeeApplicationUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class EmployeeEntityServiceTest extends EmployeeApplicationUnitTest {

    private EmployeeService employeeService;

    @Override
    @BeforeEach
    protected void beforeEach() {
        super.beforeEach();

        this.employeeService = new EmployeeService(
                jsonComponent,
                entityMapperSpy,
                employeeRepositoryMock
        );
    }

    @Test
    void success() {
        final long employeeId = 1L;
        final String name = "name";
        final var departmentEnumeration = DepartmentEnumeration.DIGITAL;

        final CreateEmployeeRequestDto createEmployeeRequestDto = CreateEmployeeRequestDto.builder()
                .name(name)
                .department(departmentEnumeration)
                .build();

        final EmployeeEntity employeeEntity = entityMapperSpy.fromDto(createEmployeeRequestDto);
        employeeEntity.setId(employeeId);

        doReturn(employeeEntity)
                .when(employeeRepositoryMock)
                .save(any());

        final CreateEmployeeResponseDto createEmployeeResponseDto = employeeService.createEmployee(createEmployeeRequestDto);

        Assertions.assertEquals(employeeId, createEmployeeResponseDto.getId());
        Assertions.assertEquals(name, createEmployeeResponseDto.getName());
        Assertions.assertEquals(departmentEnumeration.name(), createEmployeeResponseDto.getDepartment());
    }
}
