# 🏦 Copilot Enterprise Guidelines -- Spring Boot 3 (Corporate Strict Edition v6)

Generated on: 2026-02-27T12:29:12.461620 UTC

------------------------------------------------------------------------

# 🎯 OBJECTIVE

Mandatory rules for generating enterprise-grade Spring Boot 3
applications.

Strict architecture. Strict testing. Strict observability. No shortcuts.

------------------------------------------------------------------------

# 1️⃣ TECH STACK

-   Java 21
-   Spring Boot 3.x
-   Spring Web (MVC synchronous)
-   Spring Data JPA
-   Jakarta Validation
-   PostgreSQL (prod)
-   H2 (dev only)
-   Lombok (controlled usage)
-   MapStruct
-   Micrometer Tracing
-   Zipkin
-   JaCoCo (\>= 85% LINE, \>= 80% BRANCH mandatory)
-   JUnit 5
-   Mockito
-   AssertJ
-   **Testcontainers (MANDATORY for integration tests)**
-   Maven
-   Checkstyle
-   SpotBugs

------------------------------------------------------------------------

# 2️⃣ ARCHITECTURE (STRICT CLEAN ARCHITECTURE)

com.company.project ├── api ├── application │ ├── service │ ├── mapper │
└── aspect ├── domain ├── infrastructure ├── dto ├── config ├── tracing
├── exception └── bootstrap

Rules:

-   No static utility classes.
-   No field injection.
-   Constructor injection only.
-   Domain enforces invariants.
-   No cross-layer dependencies.
-   Controllers never access repositories directly.

------------------------------------------------------------------------

# 3️⃣ BEAN‑ONLY POLICY

Forbidden:

-   static constants classes
-   static helpers
-   static util methods

All reusable data must be inside Spring-managed Beans.

------------------------------------------------------------------------

# 4️⃣ STRING POLICY

-   No inline strings.
-   No hardcoded endpoint paths.
-   No hardcoded error messages.
-   No magic numbers.

All reusable values must be provided through injected Beans.

------------------------------------------------------------------------

# 5️⃣ CONFIGURATION POLICY

Mandatory:

-   @ConfigurationProperties
-   @ConfigurationPropertiesScan
-   Immutable record-based configs

Forbidden:

-   @Value
-   Direct Environment access outside config package

------------------------------------------------------------------------

# 6️⃣ NAMED BOOLEAN RULE

Extract complex conditions into expressive methods.

Forbidden:

if (x != null && x.getStatus().equals("ACTIVE") && amount \> 0)

Required:

if (isActiveWithPositiveAmount(order))

------------------------------------------------------------------------

# 7️⃣ LOGGING STANDARD

-   Structured JSON logs required
-   TraceId and SpanId required in all logs
-   No sensitive data logging
-   No debug logging in prod
-   SLF4J + @Slf4j

------------------------------------------------------------------------

# 8️⃣ AOP REQUIREMENT

Mandatory aspects:

-   LoggingAspect
-   ExecutionTimeAspect
-   ExceptionHandlingAspect
-   CorrelationIdAspect

No duplicated manual logging in service methods.

------------------------------------------------------------------------

# 9️⃣ DISTRIBUTED TRACING

Mandatory:

-   Micrometer Tracing
-   Zipkin integration
-   W3C Trace Context propagation
-   Tracing enabled in all environments

Default Zipkin:

http://localhost:9411

------------------------------------------------------------------------

# 🔟 DATABASE RULES

-   open-in-view=false
-   UUID primary keys
-   Explicit indexes
-   No EAGER by default
-   No N+1
-   Pagination mandatory
-   All timestamps in UTC
-   Use Instant or OffsetDateTime (UTC)

------------------------------------------------------------------------

# 1️⃣1️⃣ PROFILE STRATEGY

Profiles:

-   dev
-   test
-   int
-   qa
-   prod

dev → H2 + Swagger\
prod → PostgreSQL only

Run dev:

mvn spring-boot:run -Dspring-boot.run.profiles=dev

------------------------------------------------------------------------

# 1️⃣2️⃣ TESTING STRATEGY (STRICT)

## Unit Tests

-   Service layer
-   Business rules
-   Exception flows
-   Edge conditions

## Integration Tests (MANDATORY)

-   @SpringBootTest
-   Real PostgreSQL via Testcontainers
-   MockMvc API validation
-   Full database interaction testing

No in-memory DB for integration tests. Testcontainers required.

------------------------------------------------------------------------

# 1️⃣3️⃣ COVERAGE POLICY

Minimum:

LINE \>= 85% BRANCH \>= 80%

Excluded from coverage:

-   dto
-   config
-   bootstrap
-   mapper

------------------------------------------------------------------------

# 1️⃣4️⃣ README REQUIREMENTS

README must include:

-   How to run dev/test/prod
-   H2 instructions (dev only)
-   Swagger URL
-   Zipkin URL
-   All curl commands
-   Coverage execution
-   Testcontainers explanation

------------------------------------------------------------------------

# 1️⃣5️⃣ SECURITY PREPARATION

-   JWT-ready structure
-   No credentials committed
-   Sensitive configs via environment variables
-   No authentication unless requested

------------------------------------------------------------------------

# 1️⃣6️⃣ FORBIDDEN PRACTICES

-   Static helpers
-   Inline strings
-   @Value
-   Complex inline conditions
-   Business logic in controllers
-   Returning entities directly
-   Skipping validation

------------------------------------------------------------------------

# 🏁 FINAL SYSTEM CHARACTERISTICS

-   Clean architecture

-   Bean-only design

-   Structured JSON logging

-   Distributed tracing enabled

-   Strongly typed configuration

-   Testcontainers mandatory

-   =85% enforced coverage

-   Enterprise-grade quality standards
