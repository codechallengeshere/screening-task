package com.service.employee.integration.controller;

import com.service.employee.constant.EmployeeControllerConstant;
import com.service.employee.integration.EmployeeEntityApplicationIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeController_deleteEmployeeIT extends EmployeeEntityApplicationIntegrationTest {

    @Test
    void success() throws Exception {
        final long employeeId = 1L;

        doNothing()
                .when(employeeRepositoryMockBean)
                .deleteById(any());

        final String url = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH + EmployeeControllerConstant.DELETE_EMPLOYEES_ENDPOINT_PATH;
        final var requestBuilder = delete(url, employeeId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }
}
