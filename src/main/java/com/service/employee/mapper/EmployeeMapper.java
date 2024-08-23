package com.service.employee.mapper;

import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.EmployeeDto;
import com.service.employee.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeEntity fromDto(CreateEmployeeRequestDto createEmployeeRequestDto);

    EmployeeDto toDto(EmployeeEntity employeeEntity);
}
