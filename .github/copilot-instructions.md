# 🏦 Copilot Enterprise Guidelines -- Spring Boot 3

# Corporate Strict Edition v7

Generated on: 2026-02-27T12:29:12 UTC

  -----------------------------------------------------------------------
  \# 🎯 OBJECTIVE
  -----------------------------------------------------------------------
  \# 1️⃣ TECH STACK

  Mandatory:

  \- Java 21 - Spring Boot 3.x - Spring Web (MVC synchronous only) -
  Spring Data JPA - Jakarta Validation - PostgreSQL (prod + integration
  tests) - H2 (dev only) - Lombok (controlled usage only) - MapStruct -
  Micrometer Tracing - Zipkin - JaCoCo (\>= 85% LINE, \>= 80% BRANCH
  mandatory) - JUnit 5 - Mockito - AssertJ - Testcontainers (MANDATORY) -
  Cucumber (BDD mandatory) - Maven - Checkstyle - SpotBugs

  Forbidden:

  \- WebFlux - In-memory DB for integration tests - Deprecated Spring
  APIs
  -----------------------------------------------------------------------

# 2️⃣ ARCHITECTURE (STRICT CLEAN ARCHITECTURE)

Package structure mandatory:

com.company.project ├── api │ ├── controller │ ├── version │ └──
resolver ├── application │ ├── service │ ├── mapper │ └── aspect ├──
domain ├── infrastructure ├── dto ├── config ├── tracing ├── exception
└── bootstrap

Rules:

-   Controllers never access repositories directly.
-   No cross-layer dependencies.
-   Domain enforces invariants.
-   Constructor injection only.
-   No field injection.
-   No cyclic dependencies.
-   No business logic in controllers.
-   No entity returned directly.

  -----------------------------------------------------------------------
  \# 3️⃣ BEAN-ONLY POLICY
  -----------------------------------------------------------------------
  \# 4️⃣ STRING POLICY

  Forbidden:

  \- Inline endpoint paths - Inline header names - Inline error
  messages - Magic numbers - Hardcoded constants

  All reusable values must be provided via:

  \- @ConfigurationProperties - Injected Beans
  -----------------------------------------------------------------------

# 5️⃣ CONFIGURATION POLICY

Mandatory:

-   @ConfigurationProperties
-   @ConfigurationPropertiesScan
-   Immutable record-based configs

Forbidden:

-   @Value
-   Direct Environment usage outside config package

  -----------------------------------------------------------------------
  \# 6️⃣ NAMED BOOLEAN RULE
  -----------------------------------------------------------------------
  \# 7️⃣ LOGGING STANDARD

  Mandatory:

  \- Structured JSON logs - TraceId and SpanId in ALL logs - SLF4J +
  @Slf4j - No sensitive data logging - No debug logging in production -
  No manual duplicated logging in services (use AOP)
  -----------------------------------------------------------------------

# 8️⃣ AOP REQUIREMENT

Mandatory Aspects:

-   LoggingAspect
-   ExecutionTimeAspect
-   ExceptionHandlingAspect
-   CorrelationIdAspect

No service may manually implement logging logic that duplicates aspects.

  -----------------------------------------------------------------------
  \# 9️⃣ DISTRIBUTED TRACING
  -----------------------------------------------------------------------
  \# 🔟 DATABASE RULES

  Mandatory:

  \- spring.jpa.open-in-view=false - UUID primary keys - Explicit DB
  indexes - No FetchType.EAGER by default - No N+1 queries - Pagination
  mandatory - All timestamps in UTC - Use Instant or OffsetDateTime (UTC)

  Integration tests MUST use real PostgreSQL container.
  -----------------------------------------------------------------------

# 1️⃣1️⃣ PROFILE STRATEGY

Profiles:

-   dev
-   test
-   int
-   qa
-   prod

Rules:

dev → H2 + Swagger prod → PostgreSQL only test/int/qa → PostgreSQL

Run dev:

mvn spring-boot:run -Dspring-boot.run.profiles=dev

  -----------------------------------------------------------------------
  \# 1️⃣2️⃣ VERSIONING POLICY (MANDATORY)
  -----------------------------------------------------------------------
  \# 1️⃣3️⃣ TEST STRATEGY (STRICT MODE)

  Testing is NOT optional.

  Every endpoint must include:

  1\. Unit Tests 2. Integration Tests (Testcontainers) 3. BDD Tests
  (Cucumber)
  -----------------------------------------------------------------------

## 1️⃣ Unit Tests (MANDATORY)

Coverage:

-   Services
-   Business rules
-   Domain invariants
-   Exception flows
-   Version resolver
-   Aspects
-   Edge cases

Tools:

-   JUnit 5
-   Mockito
-   AssertJ

  -----------------------------------------------------------------------
  \## 2️⃣ Integration Tests (MANDATORY)
  -----------------------------------------------------------------------
  \## 3️⃣ CUCUMBER BDD (MANDATORY)

  Dependencies:

  \- cucumber-java - cucumber-spring - cucumber-junit-platform-engine

  Rules:

  \- Feature files under src/test/resources/features - Steps under test
  package - Full Spring context bootstrapped - No duplicated steps

  Each endpoint requires scenarios for:

  \- Happy path - Validation failure - Version test - Unsupported
  version - Error scenario
  -----------------------------------------------------------------------

# 1️⃣4️⃣ TESTCONTAINERS POLICY

Mandatory container:

postgres:16-alpine

Must use:

-   @DynamicPropertySource
-   No manual property hacks

  -----------------------------------------------------------------------
  \# 1️⃣5️⃣ COVERAGE POLICY
  -----------------------------------------------------------------------
  \# 1️⃣6️⃣ README REQUIREMENTS (MANDATORY)

  README must include:

  1\. Architecture explanation 2. Versioning explanation 3. How to run
  dev/test/prod 4. H2 console instructions 5. Swagger URL 6. Zipkin URL
  7. ALL curl commands for EVERY endpoint and version 8. Coverage
  execution command 9. Testcontainers explanation
  -----------------------------------------------------------------------

# 1️⃣7️⃣ SECURITY PREPARATION

-   JWT-ready structure
-   No credentials committed
-   Sensitive data from environment variables only
-   No authentication unless requested

  -----------------------------------------------------------------------
  \# 1️⃣8️⃣ QUALITY GATES
  -----------------------------------------------------------------------
  \# 1️⃣9️⃣ FORBIDDEN PRACTICES

  \- Static helpers - Inline strings - @Value - Hardcoded endpoints -
  Business logic in controllers - Returning entities directly - Skipping
  validation - Using H2 outside dev - Skipping version tests - Generating
  code without tests
  -----------------------------------------------------------------------

# 🏁 FINAL SYSTEM CHARACTERISTICS

System must be:

-   Clean Architecture compliant

-   Bean-only design

-   Fully versioned per endpoint

-   Header-driven versioning

-   Structured JSON logging

-   Distributed tracing enabled

-   =85% coverage enforced

-   Integration-tested with PostgreSQL containers

-   BDD-tested with Cucumber

-   Enterprise-audit ready

-   Production-grade quality enforced

------------------------------------------------------------------------

END OF DOCUMENT Corporate Strict Edition v7
