# SIS 1 + Practice 4 (part 1): Books REST API

Spring Boot, PostgreSQL, Spring Data JPA.

## SIS 1
- CRUD on `/books` with DTOs (`BookRequest`, `BookResponse`) and Bean Validation
- Unified error format (`ErrorResponse`, `GlobalExceptionHandler`)
- Status codes: 201, 200, 204, 400, 404, 405, 409, 415
- Maven profiles: `./mvnw -Pdev` (default) or `./mvnw -Ptest`
- HTTP-client collection: `SIS1-Books-API.postman_collection.json`

## Practice 4, part 1
- PostgreSQL connection per profile (`application-dev.properties`, `application-test.properties`)
- Repository layer: `BookRepository` extends `JpaRepository`
- Paged queries: `/books/paged`, `/books/by-title`, `/books/by-author`, `/books/by-year`
  with `?page=&size=&sort=` (default size 10, max 50)

## Run
1. `brew services start postgresql@17` (port 5440, databases `sis1` and `sis1_test`)
2. Run `Sis1Application`
3. Import the Postman collection and run it