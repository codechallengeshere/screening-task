package com.service.employee.integration.controller;

import com.service.employee.constant.EmployeeControllerConstant;
import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.integration.EmployeeEntityApplicationIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeController_createEmployeeIT extends EmployeeEntityApplicationIntegrationTest {

    @Test
    void success() throws Exception {
        final long employeeId = 1L;
        final String name = "name";
        final var department = DepartmentEnumeration.DIGITAL;
        final Date employedAt = Date.from(Instant.now());

        final CreateEmployeeRequestDto createEmployeeRequestDto = CreateEmployeeRequestDto.builder()
                .name(name)
                .department(department)
                .build();

        final EmployeeEntity employeeEntity = entityMapperSpy.fromDto(createEmployeeRequestDto);
        employeeEntity.setId(employeeId);
        employeeEntity.setEmployedAt(employedAt);

        doReturn(employeeEntity)
                .when(employeeRepositoryMockBean)
                .save(any());

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.CREATE_EMPLOYEE_ENDPOINT_PATH;
        final var requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeRequestDto).getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.department").value(department.name()))
                .andExpect(jsonPath("$.employed_at").isNotEmpty());
    }
}
