# Enterprise Automation Framework

Java 21 + Maven automation framework for UI, API, and database testing.

## Technologies

- Selenium 4
- TestNG
- Rest Assured
- Extent Reports
- SQL Server

## Project structure

- `src/main/java` -- framework services, drivers, reporting, utilities
- `src/test/java` -- sample tests and suites
- `src/main/resources/config` -- environment properties

## How to run

- `mvn clean test`

## Notes

The framework uses a thread-safe `DriverManager`, centralized `ConfigManager`, and Extent Reports listeners.
