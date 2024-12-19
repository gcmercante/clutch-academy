# Clutch Academy Services

The goal of this project is to study various languages and frameworks while developing a microservices architecture for an eSports-focused learning platform.

## course-service (MongoDB)
### Responsibilities
- Create and save courses
- Retrieve all available courses
- Retrieve courses based on a filter (category, name, instructor)
- Deactivate a course
- Update a course

## user-service
### Responsibilities
- Create new users and instructors
- Deactivate users and instructors
- Update users and instructors
- Retrieve user and instructor information (based on authentication)
### Tech Stack
- Java 21
- Spring Boot 3
- PostgreSQL
### TODO
- Authentication (when auth-service is ready)
    - Modify GET endpoints to return results based on authentication
- Monitoring (when monitoring-service is ready)
- Handle Admin routes

## auth-service (Keycloak?)
### Responsibilities
- Authenticate users and instructors
- Manage endpoint permissions for services (e.g., a regular user cannot modify a course)

## upload-service (HTTP2 streaming?)
### Responsibilities
- Receive video files and other types of files and save them to the cloud
- Return saved files associated with a specific course

## monitoring-service (Kafka/RabbitMQ/Elasticsearch)
### Responsibilities
- Monitor all services
- Centralized logging for all services
- Send notifications about service status if critical

## global-api (Go?)
### Responsibilities
- Facilitate the integration of the future frontend with the backend services