package com.service.employee.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DepartmentEnumeration {

    DIGITAL("Digital"),
    FINANCE("Finance"),
    MARKETING("Marketing");

    private final String value;
}
