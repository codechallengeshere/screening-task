package com.service.employee.unit.service;

import com.service.employee.service.EmployeeService;
import com.service.employee.unit.EmployeeApplicationUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class EmployeeService_deleteEmployeeTest extends EmployeeApplicationUnitTest {

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
    void success() {
        final long employeeId = 1L;

        doNothing()
                .when(employeeRepositoryMock)
                .deleteById(any());

        Assertions.assertDoesNotThrow(
                () -> employeeService.deleteEmployee(employeeId)
        );
    }
}
