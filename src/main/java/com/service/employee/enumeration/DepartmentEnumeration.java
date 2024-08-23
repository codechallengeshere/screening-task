package com.service.employee.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.service.employee.exception.DepartmentNotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DepartmentEnumeration {

    DIGITAL("Digital"),
    FINANCE("Finance"),
    MARKETING("Marketing");

    private final String department;

    @JsonCreator
    public static DepartmentEnumeration fromValue(final String value) {
        for (final DepartmentEnumeration departmentEnumeration : values()) {
            final String currentContact = departmentEnumeration.getDepartment();

            if (currentContact.equalsIgnoreCase(value)) {
                return departmentEnumeration;
            }
        }

        throw new DepartmentNotValidException();
    }
}
