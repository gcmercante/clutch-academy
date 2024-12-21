# Clutch Academy 

The goal of this project is to study various languages and frameworks while developing a micro
services architecture for an eSports-focused learning platform.

## Services

### Auth Service
- **Technology**: Spring Boot
- **Database**: Keycloak DB (PostgreSQL)
- **Features**:
    - Integration with Keycloak for authentication

### Course Service
- **Technology**: Spring Boot
- **Database**: MongoDB
- **Features**:
    - CRUD operations for courses
    - Management of course categories and modules
    - Course search and filtering

### User Service
- **Technology**: Spring Boot
- **Database**: PostgreSQL
- **Features**:
    - CRUD operations for users
    - User profile management
    - Course history and progress tracking
    - Instructor profile management

### API Gateway
- **Technology**: Go
- **Database**: Not applicable
- **Features**:
    - Routing requests to microservices
    - Centralized authentication and authorization
    - Traffic monitoring and logging

### Frontend
- **Technology**: Next.js
- **Database**: Not applicable
- **Features**:
    - User interface for course navigation and consumption
    - Login, registration, and user profile pages
    - Integration with microservices APIs

### Notification Service
- **Technology**: Nest.js
- **Database**: Redis
- **Features**:
    - Sending notifications via email and push
    - Management of notification templates
    - Integration with messaging services (e.g., RabbitMQ)

## Project Structure

```filetree
clutch-academy
├── auth-service
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com
│   │               └── clutchacademy
│   │                   └── auth
│   │                       ├── AuthApplication.java
│   │                       ├── config
│   │                       ├── controller
│   │                       ├── domain
│   │                       ├── repository
│   │                       └── service
│   ├── pom.xml
│   └── README.md
├── course-service
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com
│   │               └── clutchacademy
│   │                   └── course
│   │                       ├── CourseApplication.java
│   │                       ├── config
│   │                       ├── controller
│   │                       ├── domain
│   │                       │   ├── model
│   │                       │   │   └── Course.java
│   │                       │   ├── repository
│   │                       │   │   └── CourseRepository.java
│   │                       │   └── service
│   │                       │       └── CourseService.java
│   ├── pom.xml
│   └── README.md
├── user-service
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com
│   │               └── clutchacademy
│   │                   └── user
│   │                       ├── UserApplication.java
│   │                       ├── config
│   │                       ├── controller
│   │                       ├── domain
│   │                       │   ├── model
│   │                       │   │   └── User.java
│   │                       │   ├── repository
│   │                       │   │   └── UserRepository.java
│   │                       │   └── service
│   │                       │       └── UserService.java
│   ├── pom.xml
│   └── README.md
├── api-gateway
│   ├── main.go
│   ├── go.mod
│   └── README.md
├── frontend
│   ├── pages
│   │   ├── index.js
│   │   └── _app.js
│   ├── public
│   ├── styles
│   ├── next.config.js
│   ├── package.json
│   └── README.md
├── notification-service
│   ├── src
│   │   └── main
│   │       └── ts
│   │           └── app
│   │               ├── notification.module.ts
│   │               ├── notification.service.ts
│   │               └── notification.controller.ts
│   ├── package.json
│   ├── tsconfig.json
│   └── README.md
└── README.md
```