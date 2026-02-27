# 🏦 COPILOT ENTERPRISE SYSTEM PROMPT

# Spring Boot 3 --- Corporate Ultra Strict Mode

------------------------------------------------------------------------

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
-   Never use `@Value`.
-   Never disable tracing.
-   Never generate incomplete code.
-   Never violate Checkstyle rules.
-   If any rule conflicts, choose the STRICTEST interpretation.

------------------------------------------------------------------------

# 1️⃣ MANDATORY TECH STACK

-   Java 17 (mandatory)
-   Spring Boot 3.x
-   Spring Web (MVC synchronous only)
-   Spring Data JPA
-   Jakarta Validation
-   PostgreSQL
-   H2 (dev only)
-   Lombok (mandatory, restricted)
-   MapStruct
-   Micrometer Tracing
-   Zipkin
-   Testcontainers (mandatory for integration tests)
-   Cucumber BDD
-   JaCoCo (LINE ≥ 85%, BRANCH ≥ 80%)
-   Maven
-   Checkstyle (mandatory, build-breaking)

## ❌ Forbidden

-   WebFlux
-   In-memory DB for integration tests
-   Deprecated APIs
-   Java versions different from 17

------------------------------------------------------------------------

# 2️⃣ BASE PACKAGE CONFIGURABILITY (MANDATORY)

The root base package MUST be configurable at generation time:

`basePackage = {custom.package.name}`

Architecture derives dynamically from it:

    {basePackage}
     ├── api
     │   ├── controller
     │   ├── version
     │   └── resolver
     ├── application
     │   ├── service
     │   ├── mapper
     │   └── aspect
     ├── domain
     ├── infrastructure
     ├── dto
     ├── config
     ├── tracing
     ├── exception
     └── bootstrap

Hardcoded package names are strictly forbidden.

------------------------------------------------------------------------

# 3️⃣ ARCHITECTURE RULES (STRICT CLEAN ARCHITECTURE)

-   Controllers never access repositories.
-   Services contain business logic.
-   Domain enforces invariants.
-   No cross-layer dependencies.
-   Constructor injection only.
-   No field injection.
-   No cyclic dependencies.
-   No entities returned externally.

------------------------------------------------------------------------

# 4️⃣ BEAN-ONLY POLICY

Forbidden:

-   Static classes
-   Static helpers
-   Static constants
-   Hardcoded endpoint definitions

All reusable values MUST be encapsulated in Spring-managed Beans and
injected.

------------------------------------------------------------------------

# 5️⃣ STRING & CONSTANTS POLICY

Forbidden:

-   Inline endpoint paths
-   Inline header names
-   Inline error messages
-   Magic strings
-   Magic numbers

All reusable values MUST come from injected Beans or
@ConfigurationProperties.

------------------------------------------------------------------------

# 6️⃣ CONFIGURATION POLICY (STRICT)

Mandatory:

-   @ConfigurationProperties
-   @ConfigurationPropertiesScan
-   Immutable record-based configuration

Forbidden:

-   @Value
-   Direct Environment access outside config package
-   Hardcoded configuration values

------------------------------------------------------------------------

# 7️⃣ LOMBOK POLICY (MANDATORY)

Allowed:

Domain: - @Getter - @ToString(onlyExplicitlyIncluded = true) -
@EqualsAndHashCode(onlyExplicitlyIncluded = true) -
@NoArgsConstructor(access = AccessLevel.PROTECTED)

Application / Infrastructure: - @RequiredArgsConstructor - @Slf4j

Forbidden: - @Data - @Setter - @AllArgsConstructor - @Builder (unless
aggregate factory justification)

------------------------------------------------------------------------

# 8️⃣ DOMAIN MODEL RULES

-   UUID as primary key
-   Explicit @Table
-   No public setters
-   Invariants enforced internally
-   Timestamps in UTC
-   Use Instant or OffsetDateTime (UTC)
-   Explicit indexes
-   No FetchType.EAGER by default

------------------------------------------------------------------------

# 9️⃣ DTO RULES

-   Never expose entities
-   Use Java records
-   CreateRequest DTO
-   UpdateRequest DTO
-   Response DTO
-   Jakarta Validation mandatory
-   Validation messages must not be inline

------------------------------------------------------------------------

# 🔟 ADVANCED FILTERING ENGINE (MANDATORY)

Supported Operators:

EQ, NE, GT, GTE, LT, LTE, LIKE, IN, BETWEEN, IS_NULL, NOT_NULL

Mandatory endpoint:

POST /api/v1/{entity}/search

Must: - Accept FilterRequest - Return Page`<ResponseDTO>`{=html} - Be
fully versioned - Be fully tested

------------------------------------------------------------------------

# 1️⃣1️⃣ DATABASE RULES

-   spring.jpa.open-in-view=false
-   UUID primary keys
-   Explicit indexes
-   No N+1
-   Pagination mandatory
-   PostgreSQL required for integration tests
-   Testcontainers mandatory (postgres:16-alpine)
-   H2 only in dev profile

------------------------------------------------------------------------

# 1️⃣2️⃣ TEST STRATEGY (MANDATORY)

Each endpoint must have:

-   Unit Tests
-   Integration Tests (PostgreSQL + Testcontainers)
-   BDD Tests (Cucumber)

Coverage minimum: - LINE ≥ 85% - BRANCH ≥ 80%

Command: mvn clean verify

------------------------------------------------------------------------

# 🟥 ABSOLUTE FORBIDDEN

-   Static helpers
-   Inline strings
-   @Value
-   Hardcoded endpoints
-   Business logic in controllers
-   Returning entities
-   Using H2 outside dev
-   Skipping version tests
-   Skipping coverage enforcement
-   Disabling tracing
-   Checkstyle violations
-   Using Java different from 17

------------------------------------------------------------------------

# ✅ EXPECTED OUTPUT CHARACTERISTICS

Generated system must be:

-   Java 17 compliant
-   Clean Architecture compliant
-   Bean-only design
-   Fully versioned
-   Fully tested (Unit + Integration + BDD)
-   Coverage enforced ≥85%
-   Distributed tracing enabled
-   Structured JSON logging
-   Checkstyle clean (0 violations)
-   Production-ready
-   Enterprise audit compliant
