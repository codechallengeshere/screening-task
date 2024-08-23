package com.service.employee.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.employee.component.JsonComponent;
import com.service.employee.mapper.EmployeeMapper;
import com.service.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public abstract class EmployeeApplicationUnitTest {

    protected JsonComponent jsonComponent;

    @Spy
    protected EmployeeMapper entityMapperSpy;

    @Mock
    protected EmployeeRepository employeeRepositoryMock;

    @BeforeEach
    protected void beforeEach() {
        final var objectMapper = new ObjectMapper();

        this.jsonComponent = new JsonComponent(objectMapper);
        this.entityMapperSpy = Mappers.getMapper(EmployeeMapper.class);
        this.employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
    }

    @AfterEach
    void afterEach() {
        // no-op
    }
}
