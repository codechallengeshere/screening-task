package com.service.employee.controller;

import com.service.employee.component.JsonComponent;
import com.service.employee.constant.EmployeeControllerConstant;
import com.service.employee.dto.ApiErrorResponseDto;
import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.CreateEmployeeResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "Endpoints for employees")
@RestController
@RequestMapping(
        value = EmployeeControllerConstant.EMPLOYEE_CONTROLLER_ENDPOINT_PREFIX_PATH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EmployeeController {

    private final JsonComponent jsonComponent;
    private final EmployeeService employeeService;

    @Operation(
            summary = "Create Employee",
            description = "Create employee using name and department"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CreateEmployeeResponseDto.class
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
    public ResponseEntity<CreateEmployeeResponseDto> createEmployee(
            @Valid @RequestBody final CreateEmployeeRequestDto createEmployeeRequestDto
    ) {
        log.debug("EmployeeController#createEmployee: start: " + jsonComponent.convertObjectToJsonString(createEmployeeRequestDto));

        final var createEmployeeResponseDto = employeeService.createEmployee(createEmployeeRequestDto);

        log.debug("EmployeeController#createEmployee: end: " + jsonComponent.convertObjectToJsonString(createEmployeeResponseDto));
        return new ResponseEntity<>(createEmployeeResponseDto, HttpStatus.OK);
    }
}
