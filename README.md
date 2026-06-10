# SecurePay - Intelligent Payment Gateway with Fraud Detection
Link:https://securepay-1.onrender.com/

## Overview

SecurePay is a secure online payment gateway built using Spring Boot and PostgreSQL. The platform enables users to perform transactions securely while incorporating fraud detection mechanisms, JWT-based authentication, OTP verification, and role-based access control.

The system is designed to provide a safe and reliable payment experience by identifying suspicious transactions and allowing administrators to monitor and manage transaction activities.

---

## Features

### User Authentication & Security

* User Registration
* Secure Password Hashing using BCrypt
* OTP-based Login Verification via Email
* JWT Authentication and Authorization
* Session Security
* Role-Based Access Control (USER / ADMIN)

### Transaction Management

* Create Secure Transactions
* View Transaction History
* Filter Transactions by Date Range
* Fraud Detection for Suspicious Transactions
* Manual Transaction Flagging
* Transaction Monitoring Dashboard

### Admin Features

* View All Transactions
* Monitor Flagged Transactions
* Manually Flag Suspicious Transactions
* Administrative Dashboard

### Security Features

* JWT Token Validation
* Spring Security Integration
* Protected REST APIs
* Secure Email OTP Verification
* PostgreSQL Database Security

---

## Technology Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* JWT (JSON Web Tokens)
* Thymeleaf

### Database

* PostgreSQL

### Frontend

* HTML5
* CSS3
* JavaScript
* Thymeleaf Templates

### Tools & Platforms

* Maven
* Git & GitHub
* Render Cloud Platform
* Gmail SMTP

---

## Project Architecture

```text
SecurePay
│
├── Controller Layer
├── Service Layer
├── Repository Layer
├── Entity Layer
├── Security Configuration
├── JWT Authentication Filter
├── OTP Email Service
├── PostgreSQL Database
└── Thymeleaf Frontend
```

---

## Authentication Flow

1. User registers with email and password.
2. Password is encrypted using BCrypt.
3. User logs in with email and password.
4. OTP is sent to the registered email.
5. User enters OTP for verification.
6. JWT token is generated after successful verification.
7. Protected APIs are accessed using JWT authentication.

---

## Fraud Detection Logic

The system automatically analyzes transactions and flags suspicious activities based on predefined fraud detection rules.

Examples:

* Unusually large transactions
* Repeated rapid transactions
* Manually flagged transactions by administrators

Flagged transactions are highlighted for review by administrators.

---

## API Endpoints

### User APIs

| Method | Endpoint            | Description              |
| ------ | ------------------- | ------------------------ |
| POST   | /api/users/register | Register User            |
| POST   | /api/users/login    | Login & OTP Verification |

### Transaction APIs

| Method | Endpoint          | Description           |
| ------ | ----------------- | --------------------- |
| POST   | /api/transaction  | Create Transaction    |
| GET    | /api/transactions | Get User Transactions |

### Admin APIs

| Method | Endpoint                | Description           |
| ------ | ----------------------- | --------------------- |
| GET    | /api/admin/transactions | View All Transactions |
| POST   | /api/admin/flag         | Flag Transaction      |

---

## Database

The application uses PostgreSQL for persistent storage.

Main tables:

* Users
* Transactions
* OTP Records

---

## Deployment

The application is deployed on Render and connected to a PostgreSQL database.

Deployment Features:

* Cloud Hosting
* Managed PostgreSQL Database
* Environment Variables Support
* Secure Configuration Management

---

## Future Enhancements

* Payment Routing Engine
* AI-Based Fraud Detection
* Multi-Factor Authentication
* Transaction Analytics Dashboard
* Payment Gateway Integration
* Real-Time Notifications
* Audit Logging

---

## Author

Anukrat Gupta

Java Backend Developer | Spring Boot Developer

SecurePay was developed as a full-stack secure payment gateway project to demonstrate modern backend development, authentication, authorization, fraud detection, database management, and cloud deployment using Spring Boot and PostgreSQL.
