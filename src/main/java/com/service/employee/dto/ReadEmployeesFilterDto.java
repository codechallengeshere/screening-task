package com.service.employee.dto;

import com.service.employee.enumeration.DepartmentEnumeration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadEmployeesFilterDto {

    private Integer limit;
    private Integer page;
    private DepartmentEnumeration department;
    private Integer employedAtYear;
}
