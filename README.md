# Spring Boot API — Counterparty Risk Profile Management

## Architecture

This project follows **Clean Architecture** principles with a strict layered structure:

```
com.example.api
├── api
│   ├── version       # API version enum
│   └── resolver      # Header-driven version resolver
├── application
│   ├── service       # Business logic (interfaces and implementations)
│   ├── mapper        # MapStruct mappers (entity ↔ DTO)
│   └── aspect        # AOP aspects (logging, tracing, execution time, exception handling)
├── controller        # REST controllers (no business logic)
├── domain            # JPA entities with domain invariants
├── infrastructure    # Spring Data JPA repositories
├── dto
│   ├── request       # Inbound DTOs with Jakarta validation
│   └── response      # Outbound DTOs
├── config            # @ConfigurationProperties records, security config
└── exception         # Custom exceptions and global exception handler
```

**Key Rules:**
- Controllers delegate to services — never access repositories directly.
- Entities are never returned directly; MapStruct maps them to response DTOs.
- Constructor injection only (no `@Value`, no field injection).
- Cross-cutting concerns (logging, execution time, correlation IDs, exception handling) are managed via AOP aspects.

## API Versioning

The API supports **header-driven versioning** via the `X-API-Version` HTTP header.

| Header              | Behavior                                   |
|---------------------|--------------------------------------------|
| `X-API-Version: 1`  | Routes to API version 1 (current default)  |
| Missing / blank      | Falls back to default version (`1`)        |
| Unsupported value    | Returns `400 Bad Request`                  |

Version resolution is handled by `ApiVersionResolver` using configuration from `ApiVersionProperties`.

## How to Run

### Development (H2 + Swagger)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Tests

```bash
mvn test
```

### Integration / QA / Production (PostgreSQL)

```bash
# Set required environment variables
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=springbootapi

mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## H2 Console (dev profile only)

When running with `dev` profile:

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:devdb`
- **Username:** `devuser`
- **Password:** `devpass`

## Swagger UI (dev profile only)

- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Zipkin (Distributed Tracing)

- **URL:** [http://localhost:9411](http://localhost:9411)
- Configure via `ZIPKIN_URL` environment variable.

## Curl Commands

### Hello Endpoint

```bash
curl -X GET http://localhost:8080/hello
```

### Counterparty Risk Profiles

**Create:**
```bash
curl -X POST http://localhost:8080/api/v1/counterparty-risk-profiles \
  -H "Content-Type: application/json" \
  -H "X-API-Version: 1" \
  -d '{
    "legalName": "Acme Corp",
    "countryCode": "USA",
    "creditRating": "AA+",
    "riskScore": 85.50,
    "exposureLimit": 1000000.00
  }'
```

**Get by ID:**
```bash
curl -X GET http://localhost:8080/api/v1/counterparty-risk-profiles/{id} \
  -H "X-API-Version: 1"
```

**Get all (paginated):**
```bash
curl -X GET "http://localhost:8080/api/v1/counterparty-risk-profiles?page=0&size=20" \
  -H "X-API-Version: 1"
```

**Update:**
```bash
curl -X PUT http://localhost:8080/api/v1/counterparty-risk-profiles/{id} \
  -H "Content-Type: application/json" \
  -H "X-API-Version: 1" \
  -d '{
    "legalName": "Acme Updated",
    "countryCode": "GBR",
    "creditRating": "A-",
    "riskScore": 70.00,
    "exposureLimit": 500000.00
  }'
```

**Delete:**
```bash
curl -X DELETE http://localhost:8080/api/v1/counterparty-risk-profiles/{id} \
  -H "X-API-Version: 1"
```

## Code Coverage

Run tests with JaCoCo coverage report:

```bash
mvn verify
```

Coverage report is generated at `target/site/jacoco/index.html`.

**Thresholds enforced:** ≥ 85% line coverage, ≥ 80% branch coverage.

## Testcontainers

Integration tests and Cucumber BDD tests use **Testcontainers** with `postgres:16-alpine`.

- No in-memory H2 database is used for tests.
- `@DynamicPropertySource` injects container connection properties at runtime.
- Docker must be available to run integration tests.

```bash
# Run all tests (requires Docker)
mvn test
```
