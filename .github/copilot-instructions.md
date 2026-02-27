# 🚀 Copilot Enterprise Guidelines – Spring Boot 3 (Synchronous CRUD)

Copilot must generate production-ready, enterprise-grade Spring Boot code.

Target stack:
- Java 21
- Spring Boot 3.x
- Spring Web (synchronous)
- Spring Data JPA
- Jakarta Validation
- PostgreSQL
- Maven

Never generate tutorial-style code.
Always prefer robust, maintainable, scalable solutions.

---

## 1️⃣ Architecture Rules

Follow Clean Architecture principles.

Base package structure:

```
com.company.project
 ├── api            (Controllers)
 ├── application    (Service interfaces + implementations)
 ├── domain         (Entities + business rules)
 ├── infrastructure (Repositories + configurations)
 ├── dto            (Request/Response models)
 ├── exception      (Custom exceptions + handler)
 └── config         (Global configuration)
```

Rules:

- Controllers must be thin.
- No business logic in controllers.
- Business logic belongs to Service layer.
- Domain enforces invariants.
- Persistence details must not leak outside infrastructure.
- Use constructor injection only.

---

## 2️⃣ REST API Standards

Generate synchronous REST endpoints.

Standard CRUD mapping:

```
GET     /api/v1/{entities}
GET     /api/v1/{entities}/{id}
POST    /api/v1/{entities}
PUT     /api/v1/{entities}/{id}
DELETE  /api/v1/{entities}/{id}
```

Rules:

- Always return `ResponseEntity`
- Use correct HTTP status codes:
  - 200 OK
  - 201 Created
  - 204 No Content
  - 400 Bad Request
  - 404 Not Found
  - 409 Conflict
  - 500 Internal Server Error

Never expose exceptions directly.

---

## 3️⃣ Domain Entity Rules

- Use JPA annotations.
- Explicit `@Table` with indexes when appropriate.
- Prefer UUID as primary key.
- Avoid public setters.
- Enforce invariants in constructor or methods.
- Avoid anemic domain model.
- Do not use `Optional` in entity fields.

Example principles:
- Validate constraints inside the entity.
- Keep domain expressive, not just getters/setters.

---

## 4️⃣ DTO Rules

- Never expose Entity objects in API.
- Always create:
  - `CreateRequest`
  - `UpdateRequest`
  - `Response` DTO
- Use Java records for DTOs.
- Apply Jakarta Validation annotations.
- Explicit mapping between Entity and DTO.
- Do NOT rely on reflection-based automappers unless requested.

---

## 5️⃣ Repository Rules

- Use Spring Data JPA.
- Repository interfaces in infrastructure layer.
- No direct repository access from controllers.
- Keep queries explicit if custom logic needed.
- Avoid unnecessary EAGER fetches.

---

## 6️⃣ Service Layer Rules

- Services must be annotated with `@Service`.
- Use `@Transactional` explicitly.
- Use `readOnly=true` for read operations.
- Write operations must be atomic.
- Throw domain-specific exceptions.
- Do not catch `RuntimeException` silently.

---

## 7️⃣ Exception Handling

Implement:

- Custom exceptions:
  - `ResourceNotFoundException`
  - `BusinessException`
  - `ConflictException`
  - `ValidationException`

- Global handler using `@RestControllerAdvice`

Standard error response format:

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/resource"
}
```

Never return stack traces in production responses.

---

## 8️⃣ Logging Standards

- Use SLF4J (`log.info`, `log.warn`, `log.error`)
- Log key operations at INFO
- Log business rule violations at WARN
- Log unexpected exceptions at ERROR
- Never log passwords or sensitive data

---

## 9️⃣ Validation Strategy

- Validate input with Jakarta Validation annotations.
- Re-check critical business constraints in Service/Domain.
- Fail fast.
- Return structured validation errors.

---

## 🔟 Database & Performance Rules

- Disable open-in-view.
- Avoid N+1 problems.
- Use pagination for list endpoints.
- Use `@EntityGraph` if needed.
- Always index searchable fields.
- Avoid unnecessary database roundtrips.

---

## 11️⃣ Security Readiness

- Prepare structure to support JWT later.
- Leave placeholders for security configuration.
- Do not implement authentication unless explicitly requested.

---

## 12️⃣ Testing Expectations

Generate:

- Unit tests for Service layer (Mockito + JUnit 5)
- Integration tests for controllers (MockMvc)

Must cover:
- Happy path
- Validation failures
- Not found cases
- Conflict scenarios

Use:
- JUnit 5
- Mockito
- AssertJ

---

## 13️⃣ Code Quality

- Use Java 21 features where appropriate.
- Use records for immutable DTOs.
- Follow SOLID principles.
- Avoid magic strings and numbers.
- Keep methods short and cohesive.
- Use meaningful naming.
- Prefer immutability.
- Use `final` for dependencies.

---

## 14️⃣ Mandatory Generation Workflow

When generating a CRUD resource, Copilot must:

1. Create Domain Entity
2. Create Request DTOs
3. Create Response DTO
4. Create Repository
5. Create Service interface
6. Create Service implementation
7. Create Controller
8. Create Custom Exceptions
9. Create Global Exception Handler
10. Add validation annotations
11. Add proper transactional annotations
12. Add example unit test

Never generate minimal example code.
Never generate logic inside controller.
Always aim for production-grade structure.

---

## 📌 How to Indicate the Table You Want

When requesting Copilot to generate a CRUD resource, provide a prompt like:

```
Generate a full CRUD for the table "products" with the following columns:
- id (UUID, primary key)
- name (String, not null, max 100)
- description (String, max 500)
- price (BigDecimal, not null, min 0)
- stock (Integer, not null, min 0)
- created_at (LocalDateTime, auto-generated)
- updated_at (LocalDateTime, auto-updated)

Follow the enterprise guidelines in copilot-instructions.md.
```

You can adapt the table name, columns, types, and constraints to match your needs.
Copilot will use these guidelines to generate the full architecture for that entity.
