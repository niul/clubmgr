# Copilot Instructions for clubmgr Repository

## Project Overview

**clubmgr** is a Sports Team Website Manager built with Spring Boot 3.3.11, Maven, and Java 21. It manages teams, schedules, and standings for the Bombastic FC sports team.

## Architecture

The project is organized as a Maven multi-module build with the following structure:

- **clubmgr-bfc** (Web Application): Spring Boot web app with Thymeleaf templates, Spring Security, and REST/MVC controllers. Entry point: `WebApplication.java`. Uses Spring XML config with `applicationContext.xml`.
- **clubmgr-data** (Data Processing): Handles data extraction and transformation, including web scraping via jsoup. Depends on clubmgr-db.
- **clubmgr-db** (Data Access Layer): JPA/Hibernate entities, repositories, and database logic. Uses Spring Data, caching, and validation.
- **clubmgr-email** (Email Service): Mail utilities and email sending services via Spring Mail.
- **clubmgr-jobs** (Scheduled Jobs): Batch processing jobs (e.g., FixtureStandingsJob, FixtureAvailabilityJob). Entry point: `FixtureJobApp.java`.
- **clubmgr-util** (Shared Utilities): Common utilities and security core (Spring Security).

**Key Technologies:**
- Spring Boot 3.3.11, Spring Security 6.3.3
- Hibernate 6.6.1, PostgreSQL 42.7.2, Flyway 9.22.3
- Thymeleaf templates, SLF4J/Log4j logging
- JUnit 4.11 for testing

## Build & Test Commands

**Build entire project:**
```bash
mvn clean install
```

**Build a single module:**
```bash
mvn clean install -pl clubmgr-bfc
```

**Run all tests:**
```bash
mvn test
```

**Run tests for a single module:**
```bash
mvn test -pl clubmgr-bfc
```

**Run a specific test class:**
```bash
mvn test -Dtest=TeamRepositoryTest
```

**Run a specific test method:**
```bash
mvn test -Dtest=TeamRepositoryTest#testTeamExists
```

**Start the web application (clubmgr-bfc):**
```bash
mvn spring-boot:run -pl clubmgr-bfc
```

**Start the jobs application (clubmgr-jobs):**
```bash
mvn spring-boot:run -pl clubmgr-jobs
```

## Key Conventions

### Configuration
- XML-based Spring context configuration files are in `src/main/resources/` for each module:
  - `applicationContext.xml` - Main config (loads context-*.xml files)
  - `applicationContext-db.xml` - Database config
  - `applicationContext-security.xml` - Security config
- `application.properties` contains Spring Boot properties
- `global.properties` contains application-specific settings

### Testing
- Base test class: `BaseTestCase.java` in each module (provides common test setup)
- Tests are in `src/test/java/` following the package structure of main code
- Use Spring Test for integration tests (see `ApplicationContext` setup in BaseTestCase)

### Database
- Entities in clubmgr-db use JPA annotations
- Repositories extend Spring Data interfaces
- Flyway handles migrations
- Caching is configured via ehcache.xml for performance

### Code Patterns
- Controllers in clubmgr-bfc use Spring MVC (command pattern with data objects in `command/` package)
- Validators exist alongside command objects (e.g., `PlayerFixtureInfoListValidator.java`)
- Custom converters for form binding (e.g., `StringToTeamConverter.java`)
- Service layer handles business logic across modules
- `ControllerUtility.java` and `TeamUtility.java` provide common utilities

### Module Dependencies (bottom-up)
```
clubmgr-bfc → clubmgr-data → clubmgr-db
           → clubmgr-email ↓
           → clubmgr-util  ↓
clubmgr-jobs → clubmgr-data, clubmgr-email, clubmgr-util
```

### Build Configuration
- Java 21 with preview features enabled (`--enable-preview`)
- Target is Java 21 (no legacy Java versions)
- Maven Compiler Plugin 3.11.0 is used
- Gradle files exist but Maven (pom.xml) is the primary build system

### Logging
- Uses SLF4J with Log4j backend
- Configured via logback in Spring
- Root package is `com.niulbird.clubmgr`

## Important Notes

- The project uses Spring XML context configuration in addition to Spring Boot's annotation-based config - both coexist
- Multiple Spring Boot applications exist in the same repository (web app and jobs app)
- Thymeleaf templates and static assets are in `clubmgr-bfc/src/main/resources/templates/` and `static/`
- Security configuration uses both XML (applicationContext-security.xml) and Java classes (SecurityConfig.java, PasswordConfig.java)
