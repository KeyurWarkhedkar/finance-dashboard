# Finance Dashboard

> Spring Boot backend for tracking financial records with role-based access control and dashboard analytics.

![Java 21](https://img.shields.io/badge/Java-21-blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green) ![MySQL](https://img.shields.io/badge/MySQL-orange) ![License](https://img.shields.io/badge/license-Educational-lightgrey)

---

## Features

| Module | Capabilities |
|---|---|
| **User Management** | Register users, update details & roles (Admin), view all users (Admin) |
| **Financial Records** | Create, update, delete (Analyst/Admin); filter by type, category, or date |
| **Dashboard Analytics** | Total income/expenses, net balance, category totals, monthly trend, recent activity |
| **Security** | JWT authentication, role-based authorization (Viewer / Analyst / Admin) |

---

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot
- **Security:** Spring Security (JWT)
- **ORM:** Spring Data JPA (Hibernate)
- **Database:** MySQL
- **Utilities:** Lombok, Maven

---

## Getting Started

### Prerequisites
- Java 21
- MySQL
- Maven

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/KeyurWarkhedkar/finance-dashboard.git
cd finance-dashboard
```

**2. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=DB_USERNAME
spring.datasource.password=DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
app.jwt.secret=<your-secret-key>
app.jwt.expiration-ms=86400000
```

**3. Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

**4. Access the API**
```
http://localhost:8080/api/
```

---

## API Endpoints

> All protected routes require `Authorization: Bearer <token>` in the request header.

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/login` | Login and receive JWT token |
| `POST` | `/api/auth/register` | Register a new user |

### Users *(Admin only)*
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/users` | List all users |
| `PUT` | `/api/users/{id}` | Update user details or role |

### Financial Records
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/records?userEmail=<email>` | Create a record (Analyst/Admin) |
| `GET` | `/api/records` | List records with filters |
| `PUT` | `/api/records/{id}` | Update a record |
| `DELETE` | `/api/records/{id}` | Delete a record |

### Dashboard (VIEWER)
Shows only records belonging to the authenticated user
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/dashboard/total-income` | Total income |
| `GET` | `/api/dashboard/total-expenses` | Total expenses |
| `GET` | `/api/dashboard/net-balance` | Net balance |
| `GET` | `/api/dashboard/category-totals` | Category-wise totals |
| `GET` | `/api/dashboard/monthly-trend` | Monthly trend data |
| `GET` | `/api/dashboard/recent-activity?limit=5` | Recent activity feed |

### Dashboard (ADMIN & ANALYST)
Shows aggregated records for all users
Can be used for monitoring overall performance
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin-dashboard/total-income` | Total income |
| `GET` | `/api/admin-dashboard/total-expenses` | Total expenses |
| `GET` | `/api/admin-dashboard/net-balance` | Net balance |
| `GET` | `/api/admin-dashboard/category-totals` | Category-wise totals |
| `GET` | `/api/admin-dashboard/monthly-trend` | Monthly trend data |
| `GET` | `/api/admin-dashboard/recent-activity?limit=5` | Recent activity feed |

---

## Roles & Permissions

| Role | Records Access | Dashboard Access | User Management |
|---|---|---|---|
| **Viewer** | Own records only | Own data only | — |
| **Analyst** | Read all | Full dashboard | — |
| **Admin** | Full CRUD | Full dashboard | Full access |

---

## Postman Collection

A Postman collection (`FinanceDashboard.postman_collection.json`) is included in the repository for testing all API endpoints.

---

## License

This project is for educational and assessment purposes.
