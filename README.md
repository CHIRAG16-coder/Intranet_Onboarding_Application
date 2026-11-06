🏢 Intranet Web/App Application

A centralized company intranet web application designed to simplify internal communication, employee management, document sharing, and organizational activities.

The application provides a common platform for employees and administrators to access company information, manage employee activities, share announcements, and improve workplace collaboration.

📌 Project Overview

The Intranet Web/App Application is developed using a modern full-stack architecture with React.js, Java Spring Boot, and PostgreSQL.

The project follows a monorepo structure, keeping the frontend, backend, database scripts, and documentation in one GitHub repository.

🎯 Project Objectives
Provide a centralized platform for company employees.
Simplify employee and department management.
Manage leave applications and approvals.
Share company announcements and documents.
Provide secure login and role-based access.
Improve internal communication and productivity.
🛠️ Technology Stack
Layer	Technology
Frontend	React.js
Build Tool	Vite
Backend	Java
Framework	Spring Boot
API	REST API
Database	PostgreSQL
ORM	Spring Data JPA / Hibernate
Database Migration	Flyway
Authentication	Spring Security / JWT
Version Control	Git & GitHub
Containerization	Docker
IDE	VS Code / IntelliJ IDEA
📂 Project Structure
text
intranet-web-app/
│
├── frontend/
│   ├── public/
│   └── src/
│       ├── assets/
│       ├── components/
│       ├── layouts/
│       ├── pages/
│       ├── routes/
│       ├── services/
│       ├── hooks/
│       ├── context/
│       ├── utils/
│       ├── styles/
│       ├── App.jsx
│       └── main.jsx
│
├── backend/
│   └── src/
│       ├── main/
│       │   ├── java/com/company/intranet/
│       │   │   ├── config/
│       │   │   ├── controller/
│       │   │   ├── service/
│       │   │   ├── repository/
│       │   │   ├── entity/
│       │   │   ├── dto/
│       │   │   ├── security/
│       │   │   ├── exception/
│       │   │   └── IntranetApplication.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       └── db/migration/
│       │
│       └── test/
│
├── database/
│   ├── scripts/
│   │   ├── schema.sql
│   │   └── seed.sql
│   └── README.md
│
├── docs/
│   ├── architecture.md
│   ├── api-documentation.md
│   ├── database-design.md
│   └── screenshots/
│
├── .env.example
├── .gitignore
├── docker-compose.yml
└── README.md

Folder Description
frontend: React pages, components, layouts, routing, and API integration.
backend: Spring Boot APIs, business logic, security, and database operations.
database: SQL scripts and database-related documentation.
docs: Project architecture, API documentation, database design, and screenshots.
docker-compose.yml: PostgreSQL container configuration.
.env.example: Sample environment configuration.
✨ Main Application Modules
Module	Description
Employee Dashboard	Overview of company activities and updates
Employee Management	Manage employee profiles and information
Department Management	Manage departments and teams
Employee Directory	View employee details and contact information
Leave Management	Apply for leave and manage approvals
Announcements	Publish company news and updates
Document Management	Share company policies and documents
Notifications	Display important updates and alerts
Admin Dashboard	Manage application activities
Role Management	Control user roles and permissions
🏗️ Application Architecture
text
       EMPLOYEE / ADMIN
              |
              v
       REACT FRONTEND
              |
              v
          REST APIs
              |
              v
      JAVA SPRING BOOT
              |
       -----------------
       |       |       |
    Security Services Repository
       |       |       |
       -----------------
              |
              v
         POSTGRESQL


The React frontend communicates with the Spring Boot backend through REST APIs. The backend handles business logic, authentication, authorization, and database operations.

⚙️ Prerequisites

Install the following tools before running the project:

Node.js and npm
Java JDK 17 or 21 (depending on the selected Spring Boot version)
Maven
Git
Docker Desktop
VS Code or IntelliJ IDEA
🚀 Installation & Setup

Follow these steps to run the project locally.

1. Clone the Repository
bash
git clone https://github.com/YOUR_USERNAME/intranet-web-app.git

cd intranet-web-app

2. Configure Environment Variables

Create your local environment file from the example:

bash
cp .env.example .env


Update the database credentials and other required configuration values.

Important: Never upload actual .env files, passwords, or secret keys to GitHub.

3. Start PostgreSQL

Make sure Docker Desktop is running.

bash
docker compose up -d postgres


Check the container status:

bash
docker compose ps

4. Run the Backend

Open a terminal:

bash
cd backend


Configure the following environment variables in your terminal or IDE:

text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
SERVER_PORT


Start the Spring Boot application:

Windows:

bash
mvnw.cmd spring-boot:run


Linux / macOS:

bash
./mvnw spring-boot:run


Backend URL:

text
http://localhost:8080

5. Run the Frontend

Open another terminal:

bash
cd frontend


Install dependencies:

bash
npm install


Create the frontend environment file:

bash
cp .env.example .env


Start the development server:

bash
npm run dev


Frontend URL:

text
http://localhost:5173

🗄️ Database Information

Database: PostgreSQL

Database Name: intranet_db

Main Database Tables
employees
departments
roles
permissions
announcements
leave_requests
documents
notifications
audit_logs

Database schema changes are managed using Flyway migrations.

🔐 Security Features

The application is designed with the following security practices:

Secure user authentication.
Password hashing.
JWT-based authentication.
Role-Based Access Control (RBAC).
API authorization.
Environment-based configuration.
Audit logging.
Secure database access.
🔗 API Documentation

Backend APIs are organized according to application modules.

Method	Endpoint	Description
POST	/api/auth/login	User login
POST	/api/auth/register	User registration
GET	/api/employees	Get employees
GET	/api/employees/{id}	Get employee details
POST	/api/employees	Create employee
PUT	/api/employees/{id}	Update employee
DELETE	/api/employees/{id}	Delete employee
GET	/api/departments	Get departments
GET	/api/announcements	Get announcements

Note: These are planned example endpoints. Actual availability depends on backend implementation and authorization rules.

🧪 Testing
Frontend Testing
bash
cd frontend
npm test

Backend Testing
bash
cd backend
./mvnw test

🌿 Git Branch Strategy

The project follows a simple Git branching strategy.

Branch	Purpose
main	Stable production code
develop	Development and integration
feature/*	New features
bugfix/*	Bug fixes
📤 GitHub Contribution Guidelines
Fork or clone the repository.
Create a new feature branch.
Implement your changes.
Test your changes locally.
Commit your changes with a meaningful message.
Push your branch to GitHub.
Create a Pull Request.

Example:

bash
git checkout -b feature/employee-management

git add .

git commit -m "Add employee management module"

git push origin feature/employee-management

🔮 Future Enhancements
Real-time internal messaging.
Attendance management.
Meeting room booking.
Company event calendar.
Internal file sharing.
Email notifications.
Single Sign-On (SSO).
Mobile-responsive interface.
👨‍💻 Contributors

Contributions, suggestions, and improvements are welcome.

Add contributor names and responsibilities as the project grows.

📄 License

This project is currently under development.

Add an appropriate open-source or company-specific license before distributing the project.

📌 Project Information
Detail	Information
Project Name	Intranet Web/App Application
Frontend	React.js
Backend	Java + Spring Boot
Database	PostgreSQL
Architecture	Full-Stack Monorepo
Repository	GitHub
Status	Under Development

Built to make workplace communication and employee management easier.
