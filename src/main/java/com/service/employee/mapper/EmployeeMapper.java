package com.service.employee.mapper;

import com.service.employee.dto.CreateEmployeeRequestDto;
import com.service.employee.dto.CreateEmployeeResponseDto;
import com.service.employee.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeEntity fromDto(CreateEmployeeRequestDto createEmployeeRequestDto);

    CreateEmployeeResponseDto toDto(EmployeeEntity employeeEntity);
}
