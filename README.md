# Employee Service API

* Employee Service API that have CRUD functionality.

## Description

* Employee Service API that have CRUD functionality.

## Getting Started

### Executing program

#### Using Intellij

* Install Maven dependencies
* Create runner for `com.service.employee.EmployeeApplication`

**For Production environment:**

* run docker task `services.postgres` from `docker/docker-compose.yml` path to up postgresql instance
* run EmployeeApplication

**For Development environment:**

* no need to setup and run postgresql instance because h2 in memory database configured
* for development purposes input `dev` into profile
* in active profiles use no profile or input `dev` value
* for development purposes in `application-dev.yml` flag `app.fixtures.enabled` is set to `true` by default:
    * this will generate random data of employees using `using com.service.employee.runner.FixturesLoadRunner` class

**Further steps**

* open swagger url and try execute endpoints

## Dependencies

* Java17
* PostgreSQL
* IntelliJ IDEA
* IntelliJ IDEA Plugins:
    * Lombok

## Service

* Context path - /employee

## Swagger

* Url - /employee/swagger-ui/index.html

## H2 Console

* H2 database used for development purposes
* Url - /employee/h2-console

## Notes

* Service written and tested using `IntelliJ IDEA 2024.2.0.1 (Ultimate Edition)`

## Authors

* Aleksandrs R.

## Version History

* 1.0.0
    * Initial Release
