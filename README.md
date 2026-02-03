# Health Made Easy App 

A Spring Boot REST API for tracking daily nutrition. Removes all the "fluff" that comes with traditional fitness apps.
Plans to extend to support exercise logging.
The system is built around a clean domain model: Foods are stored per-gram, meals are composed of food items and grams, 
and daily logs compute total macros.

Swagger UI can be accessed through the lin below once cloned and started app.
http://localhost:8080/swagger-ui/index.html

## Project Goal
Build a maintainable, deployable backend service that supports:
- Creating reusable foods (Converted and stored per-gram for easy future logging)
- Building meals from foods (with editable grams per meal item)
- Logging daily intake (food entries and meal entries)
- Querying totals and history per day / average over date range
- Secure multi-user ownership (users only see their own data)

## Status
- Current: Sprint 1 - MVC for Food model with unit testing and Swagger UI with temp in memory storage.
- Next: Implementing persistence with Postgres and Flyway migrations.

## Architecture (high level)
Layered Spring Boot application:
- Controller (REST endpoints, request/response DTOs)
- Service (domain rules: per-gram conversion, meal totals, daily totals)
- Repository (data access)
- Database (PostgreSQL with Flyway migrations)

## Tech Stack

### Current (already in repo)
- Java 21
- Spring Boot (Web MVC)
- Maven
- Spring Boot Actuator

### Planned / In Progress 
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
