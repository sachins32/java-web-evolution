# User Management App (JSTL & Hidden Methods)

A lightweight Java Web Application built using **Jakarta Servlet API** and **Tomcat 10**. This version enhances the basic app by introducing **JSTL** for dynamic rendering and a **Method Masking** strategy to support full CRUD operations.

## 🚀 Features
- **Welcome Page**: A dynamic JSP interface to interact with the application.
- **Create User (POST)**: Collects user data via forms and stores it in an in-memory `ArrayList`.
- **View Users (GET)**: Uses **JSTL (`<c:forEach>`)** to dynamically generate a styled table of users.
- **Update User (PUT)**: Supports editing existing user details using a hidden method trick.
- **Delete User (DELETE)**: Allows removing users from the list with a confirmation prompt.
- **Post-Redirect-Get (PRG) Pattern**: Uses `sendRedirect` to prevent duplicate form submissions on page refresh.

## 🛠 Tech Stack
- **Language**: Java 17+
- **Specification**: Jakarta EE 10 (Servlet API 6.0, JSTL 3.0)
- **Server**: Apache Tomcat 10.1.x
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA (Community Edition) with **Smart Tomcat** plugin

## 📂 Project Structure
```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── model/
│   │   │       │   └── User.java          # Data Model (POJO)
│   │   │       └── servlet/
│   │   │           └── UserServlet.java   # Controller handling CRUD logic
│   │   ├── webapp/
│   │   │   ├── index.jsp                  # Entry point (Add User Form)
│   │   │   ├── displayUsers.jsp           # View List (JSTL & Delete logic)
│   │   │   └── editUser.jsp               # Update Form (Pre-filled)
├── pom.xml                                # Project dependencies
└── README.md