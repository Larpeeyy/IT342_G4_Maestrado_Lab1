# IT342_G4_Maestrado_Lab1 Authentication System (Web & Mobile)

A full-stack user authentication system built with **Spring Boot** for the backend, **React.js** for the web application, and **Android Studio (Kotlin)** for the mobile application.

Both platforms connect to a single MySQL database and share the same authentication logic and API.

---

## System Overview

This project demonstrates:

- Secure user registration
- Secure user login
- JWT-based authentication
- Protected routes (dashboard access)
- Profile management
- Cross-platform data consistency

The web and mobile applications communicate with the same REST API.

---

## Core Features

### User Registration
- Create account with:
  - Username
  - First Name
  - Last Name
  - Email
  - Password
- Passwords are encrypted before storage.

### User Login
- Authenticate using email and password.
- JWT token is generated after successful login.

### Protected Dashboard
- Accessible only when authenticated.
- Prevents unauthorized route access.

### Profile Page
- View:
  - Email (read-only)
  - Username
  - First Name
  - Last Name
- Edit username (updates database immediately).

### Logout
- Clears stored token.
- Redirects user to login page.

### Cross-Platform Sync
- Any change in web reflects on mobile.
- Both use the same backend and database.

---

## Technology Stack

### Backend
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Security:** Spring Security + JWT
- **Database:** MySQL (XAMPP)
- **ORM:** Spring Data JPA

### Web Frontend
- **Framework:** React.js
- **Routing:** React Router
- **HTTP Client:** Fetch API
- **State Management:** Local Storage (JWT token)

### Mobile Application (Android)
- **Language:** Kotlin
- **UI:** XML Layouts
- **HTTP Client:** Retrofit
- **Architecture:** MVVM (if implemented)

---

## Database Structure

Main table: `users`

Fields:
- user_id (Primary Key)
- username
- email
- password_hash
- first_name
- last_name
- created_at

---

## How to Run

### Backend
1. Start XAMPP (Apache + MySQL).
2. Open backend in IntelliJ.
3. Run `MiniApplication`.
4. Backend runs on:  
   `http://localhost:8080`

### Web
1. Open `/web` folder in VS Code.
2. Run:
3. Web runs on:  
`http://localhost:3000`

### Mobile
1. Open project in Android Studio.
2. Make sure backend is running.
3. Use emulator or physical device.
