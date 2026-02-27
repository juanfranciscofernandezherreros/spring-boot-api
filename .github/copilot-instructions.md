# 🚀 Copilot Enterprise Guidelines -- Spring Boot 3 (Synchronous CRUD)

Version: v4\
Generated on: 2026-02-27T12:14:03.154816 UTC

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
-   Micrometer Tracing
-   Zipkin
-   Maven

No tutorial-style shortcuts. Only production-grade architecture.

------------------------------------------------------------------------

# 2️⃣ Clean Architecture

    com.company.project
     ├── api
     ├── application
     │     ├── service
     │     ├── mapper
     │     └── aspect
     ├── domain
     ├── infrastructure
     ├── dto
     ├── exception
     ├── config
     └── tracing

Rules:

-   Controllers must be thin.
-   No business logic in controllers.
-   All cross-cutting concerns via AOP.
-   No static utility classes.
-   Everything must be managed as Spring Beans.

------------------------------------------------------------------------

# 3️⃣ NO STATIC CLASSES (MANDATORY)

Forbidden:

-   static utility classes
-   static constants holders
-   static endpoint definitions
-   static configuration access

Instead:

Create dedicated Spring-managed Beans.

Example:

@Component public class ApiPaths { public String base() { return
"/api/v1"; } public String products() { return "/products"; } }

All dependencies must be injected.

------------------------------------------------------------------------

# 4️⃣ STRING & CONSTANTS POLICY

-   No inline strings.
-   No magic strings.
-   No hardcoded error messages.

All reusable strings must be encapsulated inside Spring Beans:

Examples:

-   ApiPathsBean
-   ErrorMessageCatalog
-   ValidationMessageCatalog
-   LoggingMessageCatalog

These must be injected where required.

------------------------------------------------------------------------

# 5️⃣ NAMED CONDITIONS (IF RULES)

Forbidden:

if (x != null && x.getStatus().equals("ACTIVE") && x.getAmount() \> 0)

Required:

if (isActiveOrderWithPositiveAmount(order))

Rules:

-   Complex conditions must be extracted.
-   Boolean methods must have expressive names.
-   No nested complex if-statements.
-   Prefer early returns.

------------------------------------------------------------------------

# 6️⃣ CONFIGURATION PROPERTIES (MANDATORY)

-   Use @ConfigurationProperties
-   No @Value
-   No direct Environment access
-   No hardcoded values

Example:

@ConfigurationProperties(prefix = "app.datasource") public record
DatasourceProperties( String url, String username, String password ) { }

Must use:

@ConfigurationPropertiesScan

Properties must be injected via constructor.

------------------------------------------------------------------------

# 7️⃣ LOGGING & TRACING (MANDATORY)

-   Use SLF4J.
-   No loggers manually created.
-   Use @Slf4j (Lombok).
-   Log business events at INFO.
-   Log rule violations at WARN.
-   Log unexpected errors at ERROR.

------------------------------------------------------------------------

# 8️⃣ AOP LOGGING ASPECT (REQUIRED)

All service methods must be traced via Aspect.

Create:

application.aspect.LoggingAspect

Responsibilities:

-   Log method entry
-   Log method exit
-   Log execution time
-   Log correlation id

No manual logging in every method.

------------------------------------------------------------------------

# 9️⃣ DISTRIBUTED TRACING

Use:

-   Micrometer Tracing
-   Zipkin

Mandatory:

-   TraceId and SpanId must appear in logs.
-   Propagation headers must be enabled.
-   Dev profile may include local Zipkin.

Zipkin default URL:

http://localhost:9411

No tracing disabled in prod.

------------------------------------------------------------------------

# 🔟 REST Standards

GET /api/v1/{entities} GET /api/v1/{entities}/{id} POST
/api/v1/{entities} PUT /api/v1/{entities}/{id} DELETE
/api/v1/{entities}/{id}

Paths must be provided via injected Bean.

Always return ResponseEntity. Never expose exceptions.

------------------------------------------------------------------------

# 1️⃣1️⃣ PROFILE MANAGEMENT

Profiles supported: - dev - test - prod

Run DEV:

mvn spring-boot:run -Dspring-boot.run.profiles=dev

Run PROD:

java -jar app.jar --spring.profiles.active=prod

Swagger and H2 enabled only in dev.

------------------------------------------------------------------------

# 1️⃣2️⃣ README REQUIREMENTS (MANDATORY)

README must include:

1.  How to run with dev profile.
2.  H2 access instructions.
3.  Swagger URL.
4.  All curl examples.
5.  Zipkin instructions.
6.  Database configuration.
7.  Test execution.
8.  Coverage execution.

------------------------------------------------------------------------

# 1️⃣3️⃣ CURL EXAMPLES (REQUIRED IN README)

Example:

Create:

curl -X POST http://localhost:8080/api/v1/products\
-H "Content-Type: application/json"\
-d '{"name":"Laptop","price":999.99,"stock":10}'

Get all:

curl http://localhost:8080/api/v1/products

Get by id:

curl http://localhost:8080/api/v1/products/{uuid}

Update:

curl -X PUT http://localhost:8080/api/v1/products/{uuid}\
-H "Content-Type: application/json"\
-d '{"name":"Updated","price":899.99,"stock":5}'

Delete:

curl -X DELETE http://localhost:8080/api/v1/products/{uuid}

------------------------------------------------------------------------

# 1️⃣4️⃣ DATABASE RULES

-   open-in-view = false
-   Mandatory indexes
-   UUID keys
-   No N+1
-   Pagination required

------------------------------------------------------------------------

# 1️⃣5️⃣ COVERAGE RULES

JaCoCo minimum:

LINE \>= 80%

Build must fail otherwise.

------------------------------------------------------------------------

# 1️⃣6️⃣ FORBIDDEN PRACTICES

-   Static helper classes
-   Inline strings
-   @Value injection
-   Complex inline if statements
-   Business logic in controllers
-   EAGER fetch without reason
-   Disabling tracing in prod

------------------------------------------------------------------------

# 1️⃣7️⃣ MANDATORY CRUD GENERATION WORKFLOW

1.  Domain Entity
2.  DTOs
3.  Repository
4.  Mapper
5.  Service Interface
6.  Service Implementation
7.  Logging Aspect
8.  ConfigurationProperties classes
9.  Tracing configuration
10. Controller (using injected Beans)
11. Tests
12. Coverage enforcement

------------------------------------------------------------------------

# ✅ RESULT

Enterprise-level architecture:

-   Clean architecture

-   Bean-only design (no static classes)

-   Distributed tracing enabled

-   AOP logging enforced

-   No magic strings

-   Strong typing configuration

-   =80% coverage required

-   CI/CD ready
