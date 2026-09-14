# DrWise Todo — Backend

A small todo-list REST API, built as a hands-on refresher on current Spring Boot practice: layered architecture, DTOs, Postgres with Flyway-managed schema, and OpenAPI documentation.

## Tech stack

| Concern | Choice |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.1.1 (Spring Framework 7) |
| Persistence | Spring Data JPA (Hibernate) + PostgreSQL |
| Schema management | Flyway |
| Validation | Jakarta Bean Validation |
| API docs | springdoc-openapi (Swagger UI) |
| Build | Maven |

## Architecture

Layered, API-first REST backend — no server-rendered views:

```
HTTP request
    │
    ▼
Controller (@RestController)   — HTTP concerns only: routing, status codes, request/response DTOs
    │
    ▼
Service (@Service)             — business rules, owns all repository access
    │
    ▼
Repository (Spring Data JPA)   — persistence only
    │
    ▼
PostgreSQL
```

- **DTOs** (`dtos/`) are Java records and are the only types that cross the API boundary — entities never do. `CreateTodoItemRequest` and `UpdateTodoItemRequest` each expose only the fields a client should be able to set; server-owned fields (`id`, `createdDate`, `modifiedDate`) are never client-writable.
- **Errors** are centralized in `exceptions/GlobalExceptionHandler`, a `@RestControllerAdvice` that turns domain exceptions and validation failures into [RFC 7807](https://www.rfc-editor.org/rfc/rfc7807) `ProblemDetail` responses instead of leaking stack traces.
- **Schema** is owned entirely by Flyway migrations (`src/main/resources/db/migration/`) — `spring.jpa.hibernate.ddl-auto` is intentionally unset.

## Prerequisites

- Java 25
- Docker + Docker Compose (for local Postgres)
- Maven wrapper is included (`./mvnw`) — no local Maven install required

## Getting started

1. Start Postgres (from the repo root, where `docker-compose.yml` lives):

   ```
   docker compose up -d
   ```

   Confirm it's healthy: `docker compose ps`.

2. Run the app (from `backend/`):

   ```
   ./mvnw spring-boot:run
   ```

   This activates the `dev` Spring profile by default (see `application.properties`), which points at the local Postgres container and enables seed data.

3. The API is now available at `http://localhost:8080`.

## Configuration profiles

| File | Purpose |
|---|---|
| `application.properties` | Environment-agnostic settings; sets `spring.profiles.active=dev` as the local default |
| `application-dev.properties` | Local Postgres connection details |
| `application-prod.properties` | Same connection shape, but credentials are read from `${DB_USERNAME}` / `${DB_PASSWORD}` environment variables rather than committed to the file |

Seed/demo data (`config/TodoItemDataLoader`) is gated behind `@Profile("dev")` and only runs when the table is empty — it never runs under `prod`.

## API documentation

With the app running, interactive Swagger UI is available at:

```
http://localhost:8080/api/v1/docs.html
```

Raw OpenAPI spec: `http://localhost:8080/v3/api-docs`.

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/v1/todos` | List all todo items |
| `GET` | `/api/v1/todos/{id}` | Get a single todo item |
| `POST` | `/api/v1/todos` | Create a todo item |
| `PUT` | `/api/v1/todos/{id}` | Update a todo item |
| `DELETE` | `/api/v1/todos/{id}` | Delete a todo item |

## Database

Schema is defined in `src/main/resources/db/migration/V1__create_todo_items_table.sql` and applied automatically by Flyway on startup. To add a schema change, add a new `V{n}__description.sql` file — never edit an already-applied migration.

Local connection details (matching `docker-compose.yml`):

- Host/port: `localhost:5434`
- Database: `drwise_todo_db`
- User: `drwise_todo_db_user`

## Running tests

```
./mvnw clean verify
```

## Project structure

```
src/main/java/com/wisdomtechinc/drwisetodospringboot/
├── config/          # startup configuration (dev-only seed data loader)
├── controllers/      # @RestController — HTTP layer only
├── dtos/              # request/response records + validation
├── exceptions/       # domain exceptions + global error handling
├── models/            # JPA entities
├── repositories/     # Spring Data repositories
└── services/          # business logic
```
