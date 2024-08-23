package com.service.employee.controller;

import com.service.employee.component.JsonComponent;
import com.service.employee.constant.EmployeeControllerConstant;
import com.service.employee.dto.ApiErrorResponseDto;
import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.EmployeeDto;
import com.service.employee.dto.ReadEmployeesFilterDto;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "Endpoints for employees")
@RestController
@RequestMapping(value = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH)
public class EmployeeController {

    private final JsonComponent jsonComponent;
    private final EmployeeService employeeService;

    @Operation(
            summary = "Get Employees",
            description = "Get employees"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmployeeDto.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(
            value = EmployeeControllerConstant.READ_EMPLOYEES_ENDPOINT_PATH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EmployeeDto>> readEmployees(
            @RequestParam(name = "limit", required = false, defaultValue = "10") final Integer limit,
            @RequestParam(name = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(name = "department", required = false) final String department,
            @RequestParam(name = "employed_at_year", required = false) final Integer employedAtYear
    ) {
        final ReadEmployeesFilterDto readEmployeesFilterDto = new ReadEmployeesFilterDto();
        readEmployeesFilterDto.setLimit(limit);
        readEmployeesFilterDto.setPage(ObjectUtils.isEmpty(page) || page < 0 ? 0 : page - 1);
        readEmployeesFilterDto.setDepartment(ObjectUtils.isEmpty(department) ? null : DepartmentEnumeration.fromValue(department));
        readEmployeesFilterDto.setEmployedAtYear(employedAtYear);

        log.debug("EmployeeController#readEmployees: start: " + jsonComponent.convertObjectToJsonString(readEmployeesFilterDto));

        final List<EmployeeDto> employeeDtos = employeeService.readEmployees(readEmployeesFilterDto);

        log.debug("EmployeeController#readEmployees: end: " + jsonComponent.convertObjectToJsonString(employeeDtos));
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Create Employee",
            description = "Create employee"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmployeeDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request with not valid department value or blank name value",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiErrorResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Employee cannot be saved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(
            value = EmployeeControllerConstant.CREATE_EMPLOYEE_ENDPOINT_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody final CreateEmployeeRequestDto createEmployeeRequestDto
    ) {
        log.debug("EmployeeController#createEmployee: start: " + jsonComponent.convertObjectToJsonString(createEmployeeRequestDto));

        final EmployeeDto employeeDto = employeeService.createEmployee(createEmployeeRequestDto);

        log.debug("EmployeeController#createEmployee: end: " + jsonComponent.convertObjectToJsonString(employeeDto));
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete Employee",
            description = "Delete employee"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successful operation"
                    )
            }
    )
    @DeleteMapping(
            value = EmployeeControllerConstant.DELETE_EMPLOYEES_ENDPOINT_PATH
    )
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable(name = "id") final long employeeId
    ) {
        log.debug("EmployeeController#deleteEmployee: start: id: " + employeeId);

        employeeService.deleteEmployee(employeeId);

        log.debug("EmployeeController#createEmployee: end");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
