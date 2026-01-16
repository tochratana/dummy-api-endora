# Endora Dummy API

A comprehensive Spring Boot REST API for managing tasks, users, posts, products, and comments. This project demonstrates best practices in API design, including proper exception handling, validation, pagination, and OpenAPI/Swagger documentation.

## 🚀 Features

- **Task Management** - Create, read, update, delete tasks with pagination and sorting
- **User Management** - Manage user profiles and information
- **Post Management** - Create and manage blog posts with tags
- **Product Management** - Handle product catalog
- **Comment System** - Manage comments on posts
- **Task Limits** - Daily task limits with counters
- **Automatic Cleanup** - Scheduled cleanup of expired task records
- **Global Exception Handling** - Centralized error handling
- **API Documentation** - Interactive Swagger UI with OpenAPI 3.0
- **Pagination & Sorting** - Efficient data retrieval with customizable pagination
- **Input Validation** - Comprehensive request validation using Jakarta Validation
- **CORS Support** - Cross-origin resource sharing enabled

## 📋 Prerequisites

- **Java 21** or higher
- **PostgreSQL 12** or higher
- **Gradle 8.0** or higher (included via wrapper)
- **Maven** (optional, for alternative build)

## 🛠️ Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd untitled\ folder
```

### 2. Configure Database

Update `src/main/resources/application.yml` with your PostgreSQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/test_db
    username: your_username
    password: your_password
```

### 3. Create Database

```bash
createdb test_db
```

## 🏃 Running the Application

### Using Gradle (Recommended)

```bash
# Build the project
./gradlew clean build

# Run the application
./gradlew bootRun
```

### Using Docker

```bash
# Build Docker image
docker build -t endora-dummy-api .

# Run Docker container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/test_db \
  -e SPRING_DATASOURCE_USERNAME=your_username \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  endora-dummy-api
```

### Direct Java Execution

```bash
java -jar build/libs/endora-dummy-api-0.0.1-SNAPSHOT.jar
```

## 📚 API Documentation

Once the application is running, you can access the API documentation at:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **OpenAPI YAML**: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

## 🗂️ Project Structure

```
src/main/java/space/endora/dummy/
├── DymmyApplication.java                 # Main application class
├── common/
│   ├── dto/
│   │   ├── ApiResponse.java             # Generic API response wrapper
│   │   └── PageResponse.java            # Pagination response
│   └── exception/
│       ├── BusinessException.java       # Custom business exception
│       ├── GlobalExceptionHandler.java  # Global exception handler
│       └── ResourceNotFoundException.java
├── config/
│   └── SchedulingConfig.java            # Scheduling configuration
└── features/
    ├── commend/                         # Comment feature
    │   ├── controller/
    │   ├── dto/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    ├── post/                            # Post feature
    │   ├── controller/
    │   ├── dto/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    ├── product/                         # Product feature
    │   ├── controller/
    │   ├── dto/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    ├── task/                            # Task feature
    │   ├── controller/
    │   ├── dto/
    │   ├── mapper/
    │   ├── model/
    │   ├── repository/
    │   ├── scheduler/
    │   └── service/
    └── user/                            # User feature
        ├── config/
        ├── controller/
        ├── dto/
        ├── model/
        ├── repository/
        ├── scheduler/
        └── service/
```

## 🔌 API Endpoints

### Task Management (`/tasks`)

| Method | Endpoint                | Description               |
| ------ | ----------------------- | ------------------------- |
| POST   | `/tasks`                | Create a new task         |
| GET    | `/tasks`                | Get all tasks (paginated) |
| GET    | `/tasks/{id}`           | Get task by ID            |
| GET    | `/tasks/{id}/exists`    | Check if task exists      |
| PUT    | `/tasks/{id}`           | Update a task             |
| PATCH  | `/tasks/{id}`           | Partially update a task   |
| DELETE | `/tasks/{id}`           | Delete a task             |
| GET    | `/tasks/limits`         | Get task limits and usage |
| POST   | `/tasks/cleanup/manual` | Perform manual cleanup    |
| GET    | `/tasks/cleanup/status` | Get cleanup status        |

### Other Resources

- **Users**: `/users` - User management endpoints
- **Posts**: `/posts` - Post management endpoints
- **Products**: `/products` - Product management endpoints
- **Comments**: `/comments` - Comment management endpoints

## 📝 Example Requests

### Create a Task

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My Task",
    "description": "Task description",
    "status": "PENDING"
  }'
```

### Get All Tasks with Pagination

```bash
curl "http://localhost:8080/tasks?page=0&size=10&sortBy=createdAt&sortDir=desc"
```

### Get Task Limits

```bash
curl http://localhost:8080/tasks/limits
```

## ⚙️ Configuration

### Application Properties

Edit `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: api
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/test_db
    username: tochratana
    password: 1234
  jpa:
    hibernate:
      ddl-auto: create-drop # Options: validate, update, create, create-drop
    properties:
      hibernate:
        format_sql: true
    show-sql: true

server:
  port: 8080

task-api:
  limits:
    max-tasks-per-day: 100
  cleanup:
    enabled: true
    cron: "0 0 0 * * ?" # Daily at midnight
    keep-task-counters-days: 7

springdoc:
  api-docs:
    path: /v3/api-docs
    enabled: true
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
    try-it-out-enabled: true
```

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "ClassName"

# Run with coverage
./gradlew test --info
```

## 📦 Dependencies

### Core Framework

- Spring Boot 3.5.5
- Spring Data JPA
- Spring Web

### Database

- PostgreSQL JDBC Driver
- H2 Database (for testing)

### Documentation

- SpringDoc OpenAPI 2.8.0
- Swagger UI

### Validation

- Jakarta Validation API
- Hibernate Validator

### Utilities

- Lombok 1.18.30
- MapStruct (for entity mapping)

### Testing

- JUnit 5
- Spring Boot Test

## 🔒 Security

- CORS enabled for all origins (configure as needed for production)
- JWT bearer token support configured in OpenAPI
- Input validation on all endpoints
- Global exception handling with sensitive error masking

## 📊 Pagination

All list endpoints support pagination with the following parameters:

- `page` - Page number (0-indexed, default: 0)
- `size` - Page size (default: 10)
- `sortBy` - Sort field (default: createdAt)
- `sortDir` - Sort direction: `asc` or `desc` (default: desc)

Example:

```
GET /tasks?page=1&size=20&sortBy=title&sortDir=asc
```

## 🔄 Task Scheduling

### Cleanup Service

- Automatically runs daily at midnight (configurable via cron expression)
- Removes expired task records
- Maintains task counter history

Manual trigger:

```bash
curl -X POST http://localhost:8080/tasks/cleanup/manual
```

Get status:

```bash
curl http://localhost:8080/tasks/cleanup/status
```

## 🐛 Troubleshooting

### Connection Refused

- Ensure PostgreSQL is running: `brew services list`
- Verify database connection string in `application.yml`
- Check database credentials

### Port Already in Use

```bash
# Change port in application.yml
server:
  port: 8081
```

### Gradle Build Issues

```bash
# Clear Gradle cache
./gradlew clean

# Rebuild
./gradlew build
```

## 📄 API Response Format

All API responses follow a consistent format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    // Response data
  }
}
```

Error responses:

```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -am 'Add new feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Open a Pull Request

## 📜 License

This project is licensed under the Apache License 2.0 - see [LICENSE](LICENSE) file for details.

## 📧 Contact

For questions or support, contact the Endora Team at support@endora.space

---

**Last Updated**: January 16, 2026
**Version**: 1.0.0
