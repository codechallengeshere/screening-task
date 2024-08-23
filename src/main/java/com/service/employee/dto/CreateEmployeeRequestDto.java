package com.service.employee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.employee.enumeration.DepartmentEnumeration;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequestDto {

    @NotBlank
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "department")
    private DepartmentEnumeration department;
}
