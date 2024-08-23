package com.service.employee.repository;

import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long>, PagingAndSortingRepository<EmployeeEntity, Long> {

    Page<EmployeeEntity> findAllByEmployedAtIsBetweenAndDepartment(Date employedAtStart, Date employedAtEnd, DepartmentEnumeration departmentEnumeration, PageRequest pageRequest);

    Page<EmployeeEntity> findAllByEmployedAtIsBetween(Date employedAtStart, Date employedAtEnd, PageRequest pageRequest);

    Page<EmployeeEntity> findAllByDepartment(DepartmentEnumeration departmentEnumeration, PageRequest pageRequest);
}
