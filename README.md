
# Employee Onboarding Management System – Backend

A Spring Boot REST API application designed to manage employee onboarding workflows, administrator activities, employee information, and onboarding progress.

The system provides separate functionality for administrators and employees, with JWT-based authentication and role-based authorization.

---

## 1. Project Overview

The Employee Onboarding Management System helps organizations manage their employee onboarding process digitally.

Administrators can manage employees, monitor onboarding progress, review documents, manage checklists, and track administrative activities.

Employees can access their profiles and onboarding-related functionality through authenticated APIs.

### Key Objectives

- Digitize employee onboarding processes.
- Simplify administrative operations.
- Track employee onboarding progress.
- Secure APIs using JWT authentication.
- Store application data in PostgreSQL.
- Provide REST APIs for frontend integration.

---

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database operations |
| Hibernate | ORM |
| PostgreSQL | Relational database |
| JWT | Token-based authentication |
| Maven | Dependency management |
| Swagger / OpenAPI | API documentation |
| Eclipse / STS | Development IDE |
| Postman | API testing |

---

## 3. Main Features

### Authentication and Authorization

- User registration.
- User login.
- JWT token generation.
- Role-based access control.
- ADMIN and USER roles.
- Protected REST APIs.

### Admin Module

- Admin dashboard.
- View all registered employees.
- Search employees by name or email.
- View employee details.
- Approve employee onboarding.
- Reject employee onboarding.
- Manage onboarding checklists.
- View employee documents.
- Approve and reject submitted documents.
- View administrative audit logs.

### Employee Module

- Employee registration and login.
- View personal profile.
- Manage personal information.
- Access onboarding functionality.
- Track onboarding checklist progress.
- Submit onboarding-related information.

### Document Management

- Retrieve employee documents.
- View document information.
- Approve submitted documents.
- Reject documents with a reason.
- Maintain document status.

### Audit Management

- Retrieve administrative activity logs.
- Track administrator actions.
- Maintain action descriptions and timestamps.

---

## 4. Project Architecture

The application follows a layered architecture.

```text
src/main/java/com/sumpositive/onboarding
│
├── config
│
├── controller
│   ├── admin
│   └── employee
│
├── dto
│   ├── request
│   └── response
│
├── entity
│
├── exception
│
├── repository
│
├── security
│
├── service
│   ├── admin
│   └── employee
│
└── OnboardingApplication.java
```

Note: The package structure above is a simplified overview. Refer to the actual source folders for the complete project structure.

---

## 5. Prerequisites

Install the following software before running the application:

1. Java JDK (version compatible with the project).
2. PostgreSQL.
3. Maven (or use the included Maven Wrapper).
4. Eclipse / Spring Tool Suite / IntelliJ IDEA.
5. Postman (optional).
6. Git (optional).

Check Java installation:

```bash
java -version
```

Check Maven installation:

```bash
mvn -version
```

---

## 6. Download the Project

### Option 1: Download ZIP from GitHub

1. Open this project's GitHub repository.
2. Click the green **Code** button.
3. Select **Download ZIP**.
4. Extract the downloaded ZIP file.
5. Open the extracted project folder.

### Option 2: Clone using Git

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

Navigate into the project directory:

```bash
cd YOUR_PROJECT_FOLDER
```

---

## 7. PostgreSQL Database Setup

### Step 1: Install PostgreSQL

Install PostgreSQL and open pgAdmin or your preferred PostgreSQL client.

### Step 2: Create Database

Execute:

```sql
CREATE DATABASE onboarding_db;
```

### Step 3: Configure Database Connection

Open:

```text
src/main/resources/application.properties
```

Configure your own database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/onboarding_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Important:
- Replace the username and password with your PostgreSQL credentials.
- Ensure PostgreSQL is running.
- Confirm that the database name matches your configuration.
- Check the project's existing schema and migration configuration before changing Hibernate DDL settings.

---

## 8. JWT Configuration

The application uses JWT authentication.

Configure your JWT signing secret using your local environment configuration.

Example:

```properties
jwt.secret=${JWT_SECRET}
```

Set the `JWT_SECRET` environment variable to a securely generated secret compatible with your application's JWT implementation.

Never commit real JWT secrets or passwords to GitHub.

---

## 9. Run the Application

### Using Eclipse / Spring Tool Suite

1. Open Eclipse or STS.
2. Select File → Import.
3. Choose Existing Maven Projects.
4. Browse to the extracted project folder.
5. Click Finish.
6. Wait for Maven dependencies to download.
7. Locate `OnboardingApplication.java`.
8. Right-click the file.
9. Select Run As → Spring Boot App.

### Using Maven

Open a terminal in the project root.

Run:

```bash
mvn clean install
```

Start the application:

```bash
mvn spring-boot:run
```

If the project includes Maven Wrapper, you can also use:

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

---

## 10. API Documentation

After successfully starting the application, open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger allows you to:

- View available REST APIs.
- Execute API requests.
- Provide JWT authentication tokens.
- Inspect request and response formats.
- Test application functionality.

The port may differ depending on your application configuration.

---

## 11. Authentication Workflow

1. Register an account using the registration API.
2. Log in using the authentication API.
3. Receive the JWT access token.
4. Copy the access token.
5. Open Swagger or Postman.
6. Select Bearer Token authentication.
7. Paste your token.
8. Execute protected APIs.

### Authorization Rules

| User Role | Access |
|---|---|
| ADMIN | Administrator APIs |
| USER | Authorized employee APIs |
| Unauthenticated | Public APIs only |

Administrator endpoints are protected using role-based authorization.

---

## 12. Important API Endpoints

The following are representative endpoints implemented in the project.

### Authentication

| Method | Endpoint |
|---|---|
| POST | /api/auth/register |
| POST | /api/auth/login |

### Admin Dashboard

| Method | Endpoint |
|---|---|
| GET | /api/admin/dashboard |

### Admin User Management

| Method | Endpoint |
|---|---|
| GET | /api/admin/users |
| GET | /api/admin/users/{id} |
| GET | /api/admin/users/search?keyword=John |

### Admin Checklist

| Method | Endpoint |
|---|---|
| GET | /api/admin/checklist |
| GET | /api/admin/checklist/{userId} |
| PUT | /api/admin/checklist/{id}/complete |
| PUT | /api/admin/checklist/{id}/pending |
| DELETE | /api/admin/checklist/{id} |

### Admin Document Management

| Method | Endpoint |
|---|---|
| GET | /api/admin/documents |
| GET | /api/admin/documents/user/{userId} |
| PUT | /api/admin/documents/{id}/approve |
| PUT | /api/admin/documents/{id}/reject |

### Admin Audit

| Method | Endpoint |
|---|---|
| GET | /api/admin/audit |

Note: Check Swagger UI for the complete and current API list, request formats, and implemented endpoints.

---

## 13. HTTP Response Codes

| Status Code | Description |
|---|---|
| 200 | Request successful |
| 201 | Resource created |
| 204 | Request successful, no response body |
| 400 | Invalid request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Resource not found |
| 500 | Internal server error |

---

## 14. Running Tests

Execute Maven tests:

```bash
mvn test
```

You can also test REST APIs through:

- Swagger UI
- Postman

---

## 15. Common Setup Issues

### Database Connection Failed

Check:
- PostgreSQL is running.
- Database exists.
- Username and password are correct.
- Database port is correct.

### Port Already in Use

Change the application port in application.properties:

```properties
server.port=8081
```

### JWT Authentication Failed

Check:
- JWT secret configuration.
- Token expiration.
- Authorization header.
- Bearer token format.
- User role and authorities.

### Maven Dependency Errors

Try:

```bash
mvn clean install -U
```

---

## 16. Security Notes

- Never upload passwords or JWT signing secrets.
- Do not commit database credentials.
- Use environment variables for sensitive configuration.
- Keep protected APIs behind authentication and authorization.
- Do not expose password fields in API responses.
- Configure production database security before deployment.

---

## 17. Future Enhancements

- Frontend integration.
- Advanced employee reports.
- Monthly onboarding statistics.
- Improved document storage and download security.
- Email notifications.
- Automated testing.
- Cloud deployment.
- CI/CD integration.

---

## 18. Author

Developed as an Employee Onboarding Management System using Java, Spring Boot, PostgreSQL, and JWT authentication.

---

## 19. License

This project is available for educational and development purposes.

Add an appropriate open-source license before allowing others to reuse or redistribute the project.
```

## How to add this README to GitHub

<box border radius="lg" padding={3} gap={3}>
  <row align="center" gap={2}>
    <icon name="file-text" size="xl"/>
    **README.md**
    <badge color="success">Create this file</badge>
  </row>
  1. Open your Spring Boot project root folder.
  2. Create a new file named `README.md`.
  3. Paste the complete Markdown content above.
  4. Replace the repository URL and project-specific configuration where necessary.
  5. Save the file.
  <divider color="subtle"/>
  **Push it to GitHub**
  ```bash
  git add README.md
  git commit -m "Add project documentation"
  git push origin main
  ```
</box>



