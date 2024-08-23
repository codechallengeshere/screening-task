package com.service.employee.runner;

import com.github.javafaker.Faker;
import com.service.employee.entity.EmployeeEntity;
import com.service.employee.enumeration.DepartmentEnumeration;
import com.service.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@ConditionalOnProperty(name = "app.fixtures.enabled", havingValue = "true")
@Component
public class FixturesLoadRunner implements ApplicationRunner {

    private static final int INITIAL_CAPACITY = 1000;

    private final EmployeeRepository employeeRepository;
    private final Faker faker;

    @Autowired
    public FixturesLoadRunner(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(final ApplicationArguments args) {
        insertRandomEmployees();
    }

    private void insertRandomEmployees() {
        log.debug("FixturesLoadRunner#insertRandomEmployees: start");

        final DepartmentEnumeration[] departmentEnumerations = DepartmentEnumeration.values();
        final Date dateFrom = getDateFrom();
        final Date dateTo = getDateTo();

        final List<EmployeeEntity> employeeEntities = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i <= INITIAL_CAPACITY; i++) {
            final EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(faker.name().firstName())
                    .department(departmentEnumerations[RandomUtils.nextInt(0, departmentEnumerations.length)])
                    .employedAt(faker.date().between(dateFrom, dateTo))
                    .build();

            employeeEntities.add(employeeEntity);
        }

        employeeRepository.saveAll(employeeEntities);

        log.debug("FixturesLoadRunner#insertRandomEmployees: end");
    }

    private Date getDateFrom() {
        final LocalDate localDate = LocalDate.now().minusYears(3);
        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }

    private Date getDateTo() {
        final LocalDate localDate = LocalDate.now().plusYears(3);
        final Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
