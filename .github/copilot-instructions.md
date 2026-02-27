# 🚀 Copilot Enterprise Guidelines -- Spring Boot 3 (Synchronous CRUD)

Generated on: 2026-02-27T12:07:55.342629 UTC

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
     ├── constants
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

All endpoint paths must be stored in constant classes.

Example:

public final class ApiPaths { public static final String BASE =
"/api/v1"; public static final String PRODUCTS = "/products"; private
ApiPaths() {} }

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

# 6️⃣ String & Constants Rules (MANDATORY)

ALL string literals must be extracted to constant classes.

Forbidden: - Inline endpoint strings - Inline error messages - Magic
strings inside services - Repeated literal values

Create package:

    com.company.project.constants

Examples: - ApiPaths - ErrorMessages - ValidationMessages -
LoggingMessages

Constant classes must: - Be final - Have private constructor - Contain
only public static final fields

------------------------------------------------------------------------

# 7️⃣ Configuration Properties Rules (MANDATORY)

All YAML properties must be injected using:

    @ConfigurationProperties

Do NOT use: - @Value annotations - Hardcoded configuration access -
Direct environment calls

Example:

@ConfigurationProperties(prefix = "app.datasource") public record
DatasourceProperties( String url, String username, String password ) { }

Rules: - Properties classes must live in config package - Use
@EnableConfigurationProperties or @ConfigurationPropertiesScan - Prefer
immutable records - No direct property access outside config layer

YAML values must always be mapped to strongly typed configuration
classes.

------------------------------------------------------------------------

# 8️⃣ Lombok Rules

Allowed: - @Getter - @RequiredArgsConstructor - @Slf4j -
@NoArgsConstructor(access = PROTECTED)

Forbidden: - @Data on entities - Public setters in domain

------------------------------------------------------------------------

# 9️⃣ MapStruct Rules

-   componentModel = "spring"
-   Explicit mappings
-   Use @MappingTarget for updates
-   Located in application.mapper

------------------------------------------------------------------------

# 🔟 Service Rules

-   @Service
-   @Transactional
-   readOnly = true for reads
-   Atomic write operations
-   Throw domain-specific exceptions
-   Log responsibly (INFO, WARN, ERROR)
-   No magic strings (use constants)

------------------------------------------------------------------------

# 1️⃣1️⃣ Repository Rules

-   Spring Data JPA
-   Infrastructure layer only
-   Avoid EAGER
-   Use pagination
-   Avoid N+1

------------------------------------------------------------------------

# 1️⃣2️⃣ Exception Handling

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

No stack traces in responses. Error messages must be stored in
constants.

------------------------------------------------------------------------

# 1️⃣3️⃣ Swagger (Dev Only)

-   Enabled only in profile `dev`
-   URL: http://localhost:8080/swagger-ui.html
-   Disabled in production

------------------------------------------------------------------------

# 1️⃣4️⃣ H2 (Dev Only)

-   URL: http://localhost:8080/h2-console
-   jdbc:h2:mem:devdb
-   username: devuser
-   password: devpass
-   Disabled outside dev

------------------------------------------------------------------------

# 1️⃣5️⃣ Validation Strategy

-   Validate at DTO level
-   Re-check business rules in service/domain
-   Fail fast
-   Structured errors
-   No inline validation messages (use constants)

------------------------------------------------------------------------

# 1️⃣6️⃣ Coverage Rules

JaCoCo minimum: - LINE \>= 80%

Build must fail if below threshold.

Exclude: - dto - config - exception handler - mapper

CI command:

mvn clean verify

------------------------------------------------------------------------

# 1️⃣7️⃣ Profile Management

Profiles supported: - dev - test - prod

Run:

DEV: mvn spring-boot:run -Dspring-boot.run.profiles=dev

TEST: mvn spring-boot:run -Dspring-boot.run.profiles=test

PROD: mvn spring-boot:run -Dspring-boot.run.profiles=prod

Use spring.profiles.active only.

------------------------------------------------------------------------

# 1️⃣8️⃣ Mandatory CRUD Generation Workflow

1.  Domain Entity
2.  Request DTOs
3.  Response DTO
4.  Repository
5.  Mapper
6.  Service Interface
7.  Service Implementation
8.  Controller (using path constants)
9.  Exceptions (using message constants)
10. ConfigurationProperties mapping if needed
11. Tests
12. Coverage enforcement

Never generate minimal example code. Never mix architectural layers.
Never use inline strings. Never use @Value for configuration injection.
