# User Management App (JDBC & Docker)

A lightweight Java Web Application built using **Jakarta Servlet API** and **Tomcat 10**. This version officially moves the application from volatile in-memory storage to a persistent **MySQL 8.0** database, managed entirely via **Docker Compose**.

## 🚀 Features
- **Welcome Page**: A dynamic JSP interface to interact with the application.
- **Create User (POST)**: Collects user data via forms and persists it into the MySQL database.
- **View Users (GET)**: Uses **JSTL (`<c:forEach>`)** to fetch and display users from the database.
- **Update User (PUT)**: Supports editing existing user details using the `_method` hidden parameter trick.
- **Delete User (DELETE)**: Permanently removes users from the database with a confirmation prompt.
- **Post-Redirect-Get (PRG) Pattern**: Implemented to ensure that database operations aren't duplicated on page refresh.
- **Auto-Initialization**: Database tables are created automatically on container startup via an initialization script.

## 🛠 Tech Stack
- **Language**: Java 17+
- **Database**: MySQL 8.0 (The Warehouse)
- **JDBC Driver**: MySQL Connector/J (The Translator)
- **Specification**: Jakarta EE 10 (Servlet API 6.0, JSTL 3.0)
- **Design Pattern**: DAO (Data Access Object)
- **Server**: Apache Tomcat 10.1.x
- **Build Tool**: Maven

## 📂 Project Structure
```text
UserManagementApp/
├── db/
│   └── init.sql                   # Database schema (auto-run by Docker)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── model/
│   │   │       │   └── User.java          # Data Model (POJO)
│   │   │       ├── dao/
│   │   │       │   └── UserDAO.java       # JDBC Database logic (CRUD)
│   │   │       └── servlet/
│   │   │           └── UserServlet.java   # Controller handling request routing
├── docker-compose.yml             # Docker MySQL configuration
└── pom.xml                        # Project dependencies
```

## 🚀 Key Implementation Details

### 1. Data Access Object (DAO) Pattern
The application separates database logic from the Servlet using a `UserDAO` class. This ensures the Servlet only handles web requests while the DAO handles the "Warehouse" (MySQL).

### 2. Understanding JDBC Execution Methods
The `UserDAO` utilizes specific JDBC methods based on the SQL operation:

| Method | SQL Type | Return Value | Success Indicator |
| :--- | :--- | :--- | :--- |
| **`executeQuery()`** | `SELECT` | `ResultSet` | `rs.next()` is true |
| **`executeUpdate()`** | `INSERT, UPDATE, DELETE` | `int` | Count > 0 |
| **`execute()`** | Any / Unknown | `boolean` | `true` (Query), `false` (Update) |

* **`executeQuery()`**: Opens a "cursor" to the data. Used in `selectAllUsers()`.
* **`executeUpdate()`**: Returns the number of rows affected. If it returns `0`, no rows matched (e.g., deleting a non-existent ID).
* **`PreparedStatement`**: Crucial for security; it sanitizes inputs to prevent **SQL Injection attacks**.

### 3. Dockerized Infrastructure
No manual MySQL installation is required on the host system.
* **Service Management**: `docker-compose up -d` launches the MySQL service.
* **Init Script**: `init.sql` is mapped to `/docker-entrypoint-initdb.d/` to ensure the table structure is ready before the app starts.
---

## ⚙️ Setup Instructions (Ubuntu)

### 1. Database Setup
From the project root, run:
```bash
  docker-compose up -d
```
Check if the container is running by typing: 
```bash
  docker ps 
```

### 2. IntelliJ Configuration
1. Ensure the **Smart Tomcat** plugin is pointing to your Tomcat 10 folder.
2. Set **Deployment Directory** to `src/main/webapp`.
3. Set **Context Path** to `/`.

### 3. Running the App
1. Start the Tomcat server via IntelliJ.
2. Open your browser to: `http://localhost:8080`.

---

## 📝 Learning Notes
* **Persistence**: Unlike the `ArrayList` version, data is now permanent on the disk.
* **Post-Redirect-Get (PRG)**: After an `executeUpdate(),` we redirect to a `GET` request. This keeps the browser history clean and prevents accidental duplicate entries if the user hits "Refresh."