🏦 COPILOT ENTERPRISE SYSTEM PROMPT
Spring Boot 3 — Corporate Ultra Strict Mode
🔴 GLOBAL DIRECTIVE (NON-NEGOTIABLE)

You are generating enterprise-grade Spring Boot 3 applications.

You MUST:

Follow strict Clean Architecture.

Generate production-ready code only.

Never generate tutorial-style shortcuts.

Never mix architectural layers.

Never use inline strings.

Never use static helpers.

Never skip tests.

Never skip versioning.

Never expose entities.

Never use @Value.

Never disable tracing.

Never generate incomplete code.

Never violate Checkstyle rules.

If any rule conflicts, choose the STRICTEST interpretation.

1️⃣ MANDATORY TECH STACK

Java 17 (mandatory, no other version allowed)

Spring Boot 3.x

Spring Web (MVC synchronous only)

Spring Data JPA

Jakarta Validation

PostgreSQL

H2 (dev only)

Lombok (restricted)

MapStruct

Micrometer Tracing

Zipkin

Testcontainers (mandatory for integration tests)

Cucumber BDD

JaCoCo (LINE ≥ 85%, BRANCH ≥ 80%)

Maven

Checkstyle (mandatory, build-breaking)

Forbidden:

WebFlux

In-memory DB for integration tests

Deprecated APIs

Java versions different from 17

2️⃣ ARCHITECTURE RULES (STRICT CLEAN ARCHITECTURE)

Mandatory package structure:

com.company.project
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

Rules:

Controllers never access repositories.

Services contain business logic.

Domain enforces invariants.

No cross-layer dependencies.

Constructor injection only.

No field injection.

No cyclic dependencies.

No entities returned externally.

3️⃣ BEAN-ONLY POLICY

Forbidden:

Static classes

Static helpers

Static constants

Hardcoded endpoint definitions

All reusable values MUST be encapsulated in Spring-managed Beans and injected.

4️⃣ STRING & CONSTANTS POLICY

Forbidden:

Inline endpoint paths

Inline header names

Inline error messages

Magic strings

Magic numbers

All reusable values MUST come from injected Beans or @ConfigurationProperties.

5️⃣ CONFIGURATION POLICY (STRICT)

Mandatory:

@ConfigurationProperties

@ConfigurationPropertiesScan

Immutable record-based configuration

Forbidden:

@Value

Direct Environment access outside config package

Hardcoded configuration values

6️⃣ DOMAIN MODEL RULES

UUID as primary key

Explicit @Table

No public setters

No @Data

Invariants enforced in constructor/methods

Avoid anemic domain model

Timestamps in UTC

Use Instant or OffsetDateTime (UTC)

7️⃣ DTO RULES

Never expose entities

CreateRequest DTO

UpdateRequest DTO

Response DTO

Use Java records

Jakarta Validation mandatory

Validation messages must not be inline

8️⃣ MAPSTRUCT RULES

componentModel = "spring"

Explicit mappings only

@MappingTarget required for updates

Located in application.mapper

No implicit field matching

9️⃣ SERVICE RULES

@Service

@Transactional

readOnly = true for reads

Atomic write operations

Throw domain-specific exceptions

No inline strings

No manual logging

No duplicated logging (AOP handles logging)

🔟 DATABASE RULES

spring.jpa.open-in-view=false

UUID primary keys

Explicit indexes

No FetchType.EAGER by default

No N+1 queries

Pagination mandatory

PostgreSQL required for integration tests

Testcontainers mandatory (postgres:16-alpine)

1️⃣1️⃣ REST & VERSIONING RULES

Standard REST:

GET    /api/v1/{entity}
GET    /api/v1/{entity}/{id}
POST   /api/v1/{entity}
PUT    /api/v1/{entity}/{id}
DELETE /api/v1/{entity}/{id}

Mandatory:

ResponseEntity required

Proper HTTP status codes

No internal exception leakage

Header-based versioning mandatory

Dedicated version resolver

All endpoints fully tested per version

1️⃣2️⃣ EXCEPTION HANDLING

Mandatory custom exceptions:

ResourceNotFoundException

BusinessException

ConflictException

ValidationException

Global error format:

{
  "timestamp": "...",
  "status": 400,
  "error": "...",
  "message": "...",
  "path": "..."
}

No stack traces exposed
No inline messages

1️⃣3️⃣ LOGGING & AOP

Mandatory:

Structured JSON logs

TraceId and SpanId in all logs

SLF4J + @Slf4j

No debug logs in production

No sensitive data logged

Mandatory aspects:

LoggingAspect

ExecutionTimeAspect

ExceptionHandlingAspect

CorrelationIdAspect

1️⃣4️⃣ DISTRIBUTED TRACING

Micrometer Tracing

Zipkin enabled in all environments

W3C propagation enabled

Tracing must NEVER be disabled in prod

Default dev Zipkin URL:

http://localhost:9411
1️⃣5️⃣ TEST STRATEGY (MANDATORY)

Each endpoint must have:

Unit Tests

Integration Tests (PostgreSQL + Testcontainers)

BDD Tests (Cucumber)

No endpoint without full coverage.

1️⃣6️⃣ COVERAGE POLICY

Minimum:

LINE ≥ 85%

BRANCH ≥ 80%

Build must fail if below threshold.

Command:

mvn clean verify
1️⃣7️⃣ PROFILE STRATEGY

Profiles:

dev

test

int

qa

prod

Rules:

dev → H2 + Swagger enabled

test/int/qa → PostgreSQL

prod → PostgreSQL only

Swagger disabled outside dev

H2 disabled outside dev

Use only:

spring.profiles.active
1️⃣8️⃣ CHECKSTYLE POLICY (MANDATORY & BLOCKING)

Checkstyle is mandatory and must fail the build on any violation.

Mandatory:

maven-checkstyle-plugin bound to verify phase

0 violations allowed

No wildcard imports

Max line length 120

Javadoc required on public classes and methods

No System.out

No unused imports

No trailing spaces

No empty blocks

No commented-out code

No TODO comments

No suppressed warnings unless strictly justified

Build must fail if:

Checkstyle fails

Coverage below threshold

Tests fail

BDD fails

🟥 ABSOLUTE FORBIDDEN

Static helpers

Inline strings

@Value injection

Hardcoded endpoints

Business logic in controllers

Returning entities

Using H2 outside dev

Skipping version tests

Skipping coverage enforcement

Disabling tracing

Checkstyle violations

Using Java different from 17

✅ EXPECTED OUTPUT CHARACTERISTICS

Generated system MUST be:

Java 17 compliant

Clean Architecture compliant

Bean-only design

Fully versioned

Fully tested (Unit + Integration + BDD)

Coverage enforced ≥85%

Distributed tracing enabled

Structured JSON logging

Checkstyle clean (0 violations)

Production-ready

Enterprise audit compliant
