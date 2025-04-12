# 🏨 Hotel Management System

A comprehensive **Spring Boot**-based backend system that allows admins and users to manage hotel rooms, bookings, and users efficiently. The project includes features like user authentication, room availability checking, booking management, and secure API access control.

---

## 🔧 Technologies Used

- Java 17+
- Spring Boot
- Spring Security with JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Swagger (recommended)
- Postman (optional)

---

## 📦 Features

- ✅ User Authentication & Authorization (JWT)
- ✅ Role-Based Access Control (ADMIN, USER)
- ✅ Room Management (CRUD)
- ✅ Booking System with Confirmation Codes
- ✅ Profile and Booking History Views
- ✅ Validations & Custom Error Handling

---

## 📌 API Endpoints

### 🔐 Auth Endpoints (`/auth`)

| Method | Endpoint         | Description           | Access  |
|--------|------------------|-----------------------|---------|
| POST   | `/auth/register` | Register new user     | Public  |
| POST   | `/auth/login`    | User login            | Public  |

---

### 🧑 User Endpoints (`/users`)

| Method | Endpoint                            | Description                           | Access        |
|--------|-------------------------------------|---------------------------------------|---------------|
| GET    | `/users/all`                        | Get all users                         | ADMIN         |
| GET    | `/users/get-by-id/{id}`             | Get user by ID                        | ADMIN/USER    |
| DELETE | `/users/delete/{id}`                | Delete user by ID                     | ADMIN/USER    |
| GET    | `/users/get-logged-in-profile-info` | Get profile of logged-in user         | USER          |
| GET    | `/users/get-user-bookings/{userId}` | Get booking history of a user         | USER/ADMIN    |

---

### 🛏️ Room Endpoints (`/rooms`)

| Method | Endpoint                                      | Description                                    | Access  |
|--------|-----------------------------------------------|------------------------------------------------|---------|
| POST   | `/rooms/add`                                  | Add a new room                                 | ADMIN   |
| GET    | `/rooms/all`                                  | Get all rooms                                  | Public  |
| GET    | `/rooms/types`                                | Get all room types                             | Public  |
| GET    | `/rooms/room-by-id/{roomId}`                  | Get room by ID                                 | Public  |
| GET    | `/rooms/all-available-rooms`                  | List all available rooms                       | Public  |
| GET    | `/rooms/available-rooms-by-date-and-type`     | Get available rooms by date & type             | Public  |
| PUT    | `/rooms/update/{roomId}`                      | Update a room                                  | ADMIN   |
| DELETE | `/rooms/delete/{roomId}`                      | Delete a room                                  | ADMIN   |

---

### 📅 Booking Endpoints (`/bookings`)

| Method | Endpoint                                        | Description                                  | Access        |
|--------|-------------------------------------------------|----------------------------------------------|---------------|
| POST   | `/bookings/book-room/{roomId}/{userId}`         | Book a room                                  | ADMIN/USER    |
| GET    | `/bookings/all`                                 | Get all bookings                             | ADMIN         |
| GET    | `/bookings/get-by-confirmation-code/{code}`     | Find booking by confirmation code            | Public        |
| DELETE | `/bookings/cancel/{bookingId}`                  | Cancel a booking                             | ADMIN/USER    |

---

## 🛠️ Setup Instructions

1. **Clone the project**
   ```bash
   git clone https://github.com/your-username/HotelManagementSystem.git
   cd HotelManagementSystem

2.Configure application.properties
```bash

spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
``` 
3.Build and run
```bash
mvn clean install
mvn spring-boot:run
```

4.Access your APIs
```bash
Swagger UI: http://localhost:8080/swagger-ui/index.html

Postman for manual testing
```
## 🔐 Default Roles

ADMIN: Manage all rooms, bookings, and users.

USER: Book rooms, view profile & history


## 🚀Future Enhancements


1.Payment Gateway Integration (e.g. Stripe, Razorpay)

2.Admin Dashboard with Analytics

3.Email Booking Confirmations

4.PDF Invoice Generator

5.Docker & Kubernetes Deployment






