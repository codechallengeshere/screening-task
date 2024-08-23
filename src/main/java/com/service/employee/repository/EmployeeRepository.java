package com.service.employee.repository;

import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {

    EmployeeEntity findByEmployedAtAndDepartment(Date employedAt, DepartmentEnumeration departmentEnumeration);
}
