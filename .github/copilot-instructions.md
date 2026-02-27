# 🏦 COPILOT ENTERPRISE SYSTEM PROMPT

# Spring Boot 3 --- Corporate Ultra Strict Mode

## 🔴 GLOBAL DIRECTIVE (NON-NEGOTIABLE)

You are generating enterprise-grade Spring Boot 3 applications.

You MUST:

-   Follow strict Clean Architecture.
-   Generate production-ready code only.
-   Never generate tutorial-style shortcuts.
-   Never mix architectural layers.
-   Never use inline strings.
-   Never use static helpers.
-   Never skip tests.
-   Never skip versioning.
-   Never expose entities.
-   Never use @Value.
-   Never disable tracing.
-   Never generate incomplete code.

If any rule conflicts, choose the STRICTEST interpretation.

------------------------------------------------------------------------

# 1️⃣ MANDATORY TECH STACK

-   Java 21
-   Spring Boot 3.x
-   Spring Web (MVC synchronous only)
-   Spring Data JPA
-   Jakarta Validation
-   PostgreSQL
-   H2 (dev only)
-   Lombok (restricted)
-   MapStruct
-   Micrometer Tracing
-   Zipkin
-   Testcontainers (mandatory for integration tests)
-   Cucumber BDD
-   JaCoCo (LINE ≥ 85%, BRANCH ≥ 80%)
-   Maven

Forbidden:

-   WebFlux
-   In-memory DB for integration tests
-   Deprecated APIs

------------------------------------------------------------------------

# 2️⃣ ARCHITECTURE RULES (STRICT CLEAN ARCHITECTURE)

Mandatory package structure:

com.company.project ├── api │ ├── controller │ ├── version │ └──
resolver ├── application │ ├── service │ ├── mapper │ └── aspect ├──
domain ├── infrastructure ├── dto ├── config ├── tracing ├── exception
└── bootstrap

Rules:

-   Controllers never access repositories.
-   Services contain business logic.
-   Domain enforces invariants.
-   No cross-layer dependencies.
-   Constructor injection only.
-   No field injection.
-   No cyclic dependencies.
-   No entities returned externally.

------------------------------------------------------------------------

# 3️⃣ BEAN-ONLY POLICY

Forbidden:

-   static classes
-   static helpers
-   static constants
-   hardcoded endpoint definitions

All reusable values must be encapsulated in Spring-managed Beans and
injected.

------------------------------------------------------------------------

# 4️⃣ STRING & CONSTANTS POLICY

Forbidden:

-   Inline endpoint paths
-   Inline header names
-   Inline error messages
-   Magic strings
-   Magic numbers

All reusable values must be provided via injected Beans or
@ConfigurationProperties.

------------------------------------------------------------------------

# 5️⃣ CONFIGURATION POLICY (STRICT)

Mandatory:

-   @ConfigurationProperties
-   @ConfigurationPropertiesScan
-   Immutable record-based configuration

Forbidden:

-   @Value
-   Direct Environment access outside config package
-   Hardcoded configuration values

------------------------------------------------------------------------

# 6️⃣ DOMAIN MODEL RULES

-   UUID as primary key
-   Explicit @Table
-   No public setters
-   No @Data
-   Enforce invariants in constructor/methods
-   Avoid anemic domain model
-   Timestamps in UTC
-   Use Instant or OffsetDateTime (UTC)

------------------------------------------------------------------------

# 7️⃣ DTO RULES

-   Never expose entities
-   CreateRequest DTO
-   UpdateRequest DTO
-   Response DTO
-   Use Java records
-   Jakarta Validation mandatory
-   Validation messages must not be inline

------------------------------------------------------------------------

# 8️⃣ MAPSTRUCT RULES

-   componentModel = "spring"
-   Explicit mappings
-   @MappingTarget for updates
-   Located in application.mapper

------------------------------------------------------------------------

# 9️⃣ SERVICE RULES

-   @Service
-   @Transactional
-   readOnly = true for reads
-   Atomic write operations
-   Throw domain-specific exceptions
-   No inline strings
-   No duplicated logging (AOP handles logging)

------------------------------------------------------------------------

# 🔟 DATABASE RULES

-   spring.jpa.open-in-view=false
-   UUID primary keys
-   Explicit indexes
-   No FetchType.EAGER by default
-   No N+1 queries
-   Pagination mandatory
-   PostgreSQL required for integration tests
-   Testcontainers mandatory (postgres:16-alpine)

------------------------------------------------------------------------

# 1️⃣1️⃣ REST & VERSIONING RULES

Standard REST:

GET /api/v1/{entity} GET /api/v1/{entity}/{id} POST /api/v1/{entity} PUT
/api/v1/{entity}/{id} DELETE /api/v1/{entity}/{id}

Requirements:

-   ResponseEntity mandatory
-   Proper HTTP status codes
-   No internal exception leakage
-   Header-based versioning mandatory
-   Dedicated version resolver
-   All endpoints fully tested per version

------------------------------------------------------------------------

# 1️⃣2️⃣ EXCEPTION HANDLING

Mandatory custom exceptions:

-   ResourceNotFoundException
-   BusinessException
-   ConflictException
-   ValidationException

Global error format:

{ "timestamp": "...", "status": 400, "error": "...", "message": "...",
"path": "..." }

-   No stack traces exposed
-   No inline messages

------------------------------------------------------------------------

# 1️⃣3️⃣ LOGGING & AOP

Mandatory:

-   Structured JSON logs
-   TraceId and SpanId in all logs
-   SLF4J + @Slf4j
-   No debug logs in production
-   No sensitive data logged

Mandatory aspects:

-   LoggingAspect
-   ExecutionTimeAspect
-   ExceptionHandlingAspect
-   CorrelationIdAspect

No manual duplicated logging in services.

------------------------------------------------------------------------

# 1️⃣4️⃣ DISTRIBUTED TRACING

-   Micrometer Tracing
-   Zipkin enabled in all environments
-   W3C propagation enabled
-   Tracing must never be disabled in prod

Default dev Zipkin URL:

http://localhost:9411

------------------------------------------------------------------------

# 1️⃣5️⃣ TEST STRATEGY (MANDATORY)

Testing is NOT optional.

Each endpoint must have:

1.  Unit Tests
2.  Integration Tests (Testcontainers)
3.  BDD Tests (Cucumber)

------------------------------------------------------------------------

# 1️⃣6️⃣ COVERAGE POLICY

Minimum:

-   LINE ≥ 85%
-   BRANCH ≥ 80%

Build must fail if below threshold.

Command:

mvn clean verify

------------------------------------------------------------------------

# 1️⃣7️⃣ PROFILE STRATEGY

Profiles:

-   dev
-   test
-   int
-   qa
-   prod

Rules:

-   dev → H2 + Swagger enabled
-   test/int/qa → PostgreSQL
-   prod → PostgreSQL only
-   Swagger disabled outside dev
-   H2 disabled outside dev

Use only:

spring.profiles.active

------------------------------------------------------------------------

# 🟥 ABSOLUTE FORBIDDEN

-   Static helper classes
-   Inline strings
-   @Value injection
-   Hardcoded endpoints
-   Business logic in controllers
-   Returning entities
-   Using H2 outside dev
-   Skipping version tests
-   Skipping coverage enforcement
-   Generating code without tests
-   Disabling tracing in production

------------------------------------------------------------------------

# ✅ EXPECTED OUTPUT CHARACTERISTICS

Generated system MUST be:

-   Clean Architecture compliant
-   Bean-only design
-   Fully versioned
-   Fully tested (Unit + Integration + BDD)
-   Coverage enforced ≥85%
-   Distributed tracing enabled
-   Structured JSON logging
-   Production-ready
-   Enterprise audit compliant
