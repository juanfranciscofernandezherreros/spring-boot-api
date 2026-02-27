# 🚀 Copilot Enterprise Guidelines -- Spring Boot 3 (Synchronous CRUD)

Generated on: 2026-02-27T12:01:07.195371 UTC

------------------------------------------------------------------------

# 1️⃣ Target Stack

-   Java 21
-   Spring Boot 3.x
-   Spring Web (synchronous)
-   Spring Data JPA
-   Jakarta Validation
-   PostgreSQL (default)
-   H2 (dev profile only)
-   Lombok
-   MapStruct
-   JaCoCo (\>= 80% mandatory)
-   JUnit 5 + Mockito + AssertJ
-   Maven

Never generate tutorial-style code. Always generate production-ready,
enterprise-grade solutions.

------------------------------------------------------------------------

# 2️⃣ Clean Architecture

    com.company.project
     ├── api
     ├── application
     │     ├── service
     │     └── mapper
     ├── domain
     ├── infrastructure
     ├── dto
     │     ├── request
     │     └── response
     ├── exception
     └── config

Rules: - Thin controllers - No business logic in controllers - Services
contain business logic - Domain enforces invariants - Constructor
injection only - No field injection - No layer leakage

------------------------------------------------------------------------

# 3️⃣ REST Standards

GET /api/v1/{entities} GET /api/v1/{entities}/{id} POST
/api/v1/{entities} PUT /api/v1/{entities}/{id} DELETE
/api/v1/{entities}/{id}

Use ResponseEntity. Return proper HTTP status codes. Never expose
internal exceptions.

------------------------------------------------------------------------

# 4️⃣ Domain Rules

-   UUID as primary key
-   Explicit @Table
-   No public setters
-   No @Data on entities
-   Invariants enforced in constructor/methods
-   Avoid anemic model

------------------------------------------------------------------------

# 5️⃣ DTO Rules

-   Never expose entities
-   CreateRequest, UpdateRequest, Response DTO mandatory
-   Use Java records
-   Use Jakarta Validation
-   Explicit MapStruct mapping

------------------------------------------------------------------------

# 6️⃣ Lombok Rules

Allowed: - @Getter - @RequiredArgsConstructor - @Slf4j -
@NoArgsConstructor(access = PROTECTED)

Forbidden: - @Data on entities - Public setters in domain

------------------------------------------------------------------------

# 7️⃣ MapStruct Rules

-   componentModel = "spring"
-   Explicit mappings
-   Use @MappingTarget for updates
-   Located in application.mapper

------------------------------------------------------------------------

# 8️⃣ Service Rules

-   @Service
-   @Transactional
-   readOnly = true for reads
-   Atomic write operations
-   Throw domain-specific exceptions
-   Log responsibly (INFO, WARN, ERROR)

------------------------------------------------------------------------

# 9️⃣ Repository Rules

-   Spring Data JPA
-   Infrastructure layer only
-   Avoid EAGER
-   Use pagination
-   Avoid N+1

------------------------------------------------------------------------

# 🔟 Exception Handling

Custom exceptions: - ResourceNotFoundException - BusinessException -
ConflictException - ValidationException

Global handler must return:

    {
      "timestamp": "2024-01-01T12:00:00",
      "status": 400,
      "error": "Bad Request",
      "message": "Validation failed",
      "path": "/api/v1/resource"
    }

No stack traces in responses.

------------------------------------------------------------------------

# 1️⃣1️⃣ Swagger (Dev Only)

-   Enabled only in profile `dev`
-   URL: http://localhost:8080/swagger-ui.html
-   Disabled in production

------------------------------------------------------------------------

# 1️⃣2️⃣ H2 (Dev Only)

-   URL: http://localhost:8080/h2-console
-   jdbc:h2:mem:devdb
-   username: devuser
-   password: devpass
-   Disabled outside dev

------------------------------------------------------------------------

# 1️⃣3️⃣ Validation Strategy

-   Validate at DTO level
-   Re-check business rules in service/domain
-   Fail fast
-   Structured errors

------------------------------------------------------------------------

# 1️⃣4️⃣ Database Rules

-   open-in-view = false
-   Index searchable fields
-   Mandatory pagination
-   Avoid unnecessary round trips

------------------------------------------------------------------------

# 1️⃣5️⃣ Security Readiness

-   Prepare config placeholder
-   Whitelist swagger & H2 for dev
-   No JWT unless requested

------------------------------------------------------------------------

# 1️⃣6️⃣ Testing Standards

Unit tests: - Service layer - Mockito + JUnit 5 - AssertJ - Cover happy
path + failures

Integration tests: - MockMvc - Validate HTTP status codes

------------------------------------------------------------------------

# 1️⃣7️⃣ Coverage Rules

JaCoCo minimum: - LINE \>= 80%

Build must fail if below threshold.

Exclude: - dto - config - exception handler - mapper

CI command:

    mvn clean verify

------------------------------------------------------------------------

# 1️⃣8️⃣ Profile Management & Startup

Supported profiles: - dev - test - prod

Configuration files:

    src/main/resources/
     ├── application.yml
     ├── application-dev.yml
     ├── application-test.yml
     └── application-prod.yml

Base (application.yml): - PostgreSQL - Swagger disabled - H2 disabled -
ddl-auto = validate - open-in-view = false

Run with profiles:

DEV:

    mvn spring-boot:run -Dspring-boot.run.profiles=dev

TEST:

    mvn spring-boot:run -Dspring-boot.run.profiles=test

PROD:

    mvn spring-boot:run -Dspring-boot.run.profiles=prod

Or:

    java -jar app.jar --spring.profiles.active=prod

Note: Use `spring.profiles.active` --- NOT `-Dprofile=dev`.

Optional Maven shortcut:

    mvn spring-boot:run -Pdev

------------------------------------------------------------------------

# 1️⃣9️⃣ Mandatory CRUD Generation Workflow

1.  Domain Entity
2.  Request DTOs
3.  Response DTO
4.  Repository
5.  Mapper
6.  Service Interface
7.  Service Implementation
8.  Controller
9.  Exceptions
10. Global Handler
11. Tests
12. Coverage enforcement

Never generate minimal example code. Never mix architectural layers.
