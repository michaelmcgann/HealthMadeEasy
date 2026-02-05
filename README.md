# Health Made Easy App 

A Spring Boot REST API for tracking daily nutrition. Removes all the "fluff" that comes with traditional fitness apps.
Plans to extend to support exercise logging.
The system is built around a clean domain model: Foods are stored per-gram, meals are composed of food items and grams, 
and daily logs compute total macros.

Swagger UI can be accessed through the link below once app has started.
http://localhost:8080/swagger-ui/index.html

## Storage profiles

This project supports two storage modes via Spring profiles:

### Default: in-memory (no setup)
By default the app runs with the inmemory profile (no database required).
This is ideal for quickly cloning and running the API.

### Local Postgres (persistent)
To use Postgres locally (data persists across app restarts):
1. Start Postgres with Docker:
 - docker compose up -d
2. Run the app with the postgres profile:
 - ./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=postgres"

(If you run from IntelliJ, add --spring.profiles.active=postgres to Program Arguments, 
or set SPRING_PROFILES_ACTIVE=postgres.)

### Deployment note
In production/deployment, the app will also use the postgres profile, but it will connect to an external managed 
Postgres database (e.g., Neon/Render/etc.) via environment configuration rather than the local Docker container.

## Project Goal
Build a maintainable, deployable backend service that supports:
- Creating reusable foods (Converted and stored per-gram for easy future logging)
- Building meals from foods (with editable grams per meal item)
- Logging daily intake (food entries and meal entries)
- Querying totals and history per day / average over date range
- Secure multi-user ownership (users only see their own data)

## Status
- Current: Sprint 2: Persistence (Postgres) and Flyway migrations & Testcontainers integration tests

## Architecture (high level)
Layered Spring Boot application:
- Controller (REST endpoints, request/response DTOs)
- Service (domain rules: per-gram conversion, meal totals, daily totals)
- Repository (data access)
- Database (PostgreSQL with Flyway migrations)

## Tech Stack

- Java 21
- Spring Boot (Web MVC)
- Maven
- Spring Boot Actuator
- OpenAPI / Swagger UI (API documentation & manual testing)
- PostgreSQL (production database)
- Flyway (schema migrations)
- Spring Data JPA (persistence)
- Testcontainers (real DB integration testing in CI)
- Spring Security and JWT (authentication and user ownership)
- Docker and Docker Compose (local dev and deployment)
- GitHub Actions (CI pipeline: build/test)

## Roadmap (sprints)
- Sprint 0: Foundations and docs + running skeleton
- Sprint 1: Food model and CRUD (in-memory first)
- Sprint 2: Persistence (Postgres) and Flyway migrations
- Sprint 3: Meals (composition of foods)
- Sprint 4: Daily logging (day totals and history)
- Sprint 5: Auth and ownership (JWT)
- Sprint 6+: Pagination/search, password reset, observability, deployment

## Documentation
See `/docs` for:
- Vision & scope
- Domain glossary
- Backlog / roadmap notes
