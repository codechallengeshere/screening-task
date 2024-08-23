package com.service.employee.repository;

import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long>, PagingAndSortingRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAllByEmployedAtIsBetweenAndDepartment(Date employedAtStart, Date employedAtEnd, DepartmentEnumeration departmentEnumeration, PageRequest pageRequest);

    List<EmployeeEntity> findAllByEmployedAtIsBetween(Date employedAtStart, Date employedAtEnd, PageRequest pageRequest);

    List<EmployeeEntity> findByDepartment(DepartmentEnumeration departmentEnumeration, PageRequest pageRequest);
}
