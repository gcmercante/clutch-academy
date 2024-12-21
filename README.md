# Clutch Academy 

The goal of this project is to study various languages and frameworks while developing a micro
services architecture for an eSports-focused learning platform.

## Serviços
### Auth Service
- **Tecnologia**: Spring Boot
- **Banco de Dados**: PostgreSQL
- **Funcionalidades**:
    - Registro e login de usuários
    - Integração com Keycloak para autenticação
    - Gerenciamento de tokens JWT

### Course Service
- **Tecnologia**: Spring Boot
- **Banco de Dados**: MongoDB
- **Funcionalidades**:
    - CRUD de cursos
    - Gerenciamento de categorias e módulos de cursos
    - Busca e filtragem de cursos

### User Service
- **Tecnologia**: Spring Boot
- **Banco de Dados**: PostgreSQL
- **Funcionalidades**:
    - CRUD de usuários
    - Gerenciamento de perfis de usuário
    - Histórico de cursos e progresso
    - Gerenciamento de perfis de instrutores

### API Gateway
- **Tecnologia**: Go
- **Banco de Dados**: Não aplicável
- **Funcionalidades**:
    - Roteamento de solicitações para os microserviços
    - Autenticação e autorização centralizadas
    - Monitoramento e logging de tráfego

### Frontend
- **Tecnologia**: Next.js
- **Banco de Dados**: Não aplicável
- **Funcionalidades**:
    - Interface de usuário para navegação e consumo de cursos
    - Páginas de login, registro e perfil de usuário
    - Integração com APIs dos microserviços

### Notification Service
- **Tecnologia**: Nest.js
- **Banco de Dados**: Redis
- **Funcionalidades**:
    - Envio de notificações por email e push
    - Gerenciamento de templates de notificação
    - Integração com serviços de mensageria (ex: RabbitMQ)

## Configuração e Execução
### Auth Service
```bash
cd auth-service
./mvnw spring-boot:run
```
### Course Service
```bash
cd auth-service
./mvnw spring-boot:run
```
### User Service
```bash
cd auth-service
./mvnw spring-boot:run
```
### API Gateway
```bash
cd auth-service
./mvnw spring-boot:run
```
### Frontend
```bash
cd auth-service
./mvnw spring-boot:run
```
### Notification Service
```bash
cd auth-service
./mvnw spring-boot:run
```

## Estrutura do Projeto

```filetree
online-courses-platform
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

# Services
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

## frontend (Next.js)
