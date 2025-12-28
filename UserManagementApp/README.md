# User Management App (Servlet, Hibernate ORM, MySQL and Docker)

A robust Java Web Application built using **Jakarta Servlet API** and **Hibernate 6**. This version evolves the application from manual JDBC strings to **Object-Relational Mapping (ORM)**, allowing Java objects to be persisted automatically to a **MySQL 8.0** database.

---

## 🚀 Features
* **Welcome Page**: A dynamic JSP interface to interact with the application.
* **Create User (POST)**: Persists user entities using Hibernate's `session.persist()`.
* **View Users (GET)**: Fetches data using **HQL (Hibernate Query Language)** instead of raw SQL.
* **Update User (PUT)**: Synchronizes modified Java objects to the database using `session.merge()`.
* **Delete User (DELETE)**: Removes persistent entities via `session.remove()`.
* **Post-Redirect-Get (PRG) Pattern**: Ensures database operations aren't duplicated on page refresh.
* **Automatic Schema Generation**: Hibernate automatically manages table creation and updates via the `hbm2ddl.auto` property.

---

## 🛠 Tech Stack
* **Language**: Java 17+
* **ORM Framework**: Hibernate 6.x (Jakarta Persistence API)
* **Database**: MySQL 8.0 (The Warehouse)
* **Specification**: Jakarta EE 10 (Servlet API 6.0, JSTL 3.0)
* **Design Pattern**: DAO (Data Access Object) & Singleton (HibernateUtil)
* **Server**: Apache Tomcat 10.1.x
* **Build Tool**: Maven



---

## 📂 Project Structure
```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── model/
│   │   │       │   └── User.java          # Entity with JPA Annotations
│   │   │       ├── dao/
│   │   │       │   └── UserDAO.java       # Hibernate CRUD implementation
│   │   │       ├── util/
│   │   │       │   └── HibernateUtil.java # SessionFactory Singleton
│   │   │       └── servlet/
│   │   │           └── UserServlet.java   # Request Routing & Controller
│   │   └── resources/
│   │       └── hibernate.cfg.xml          # Database & Hibernate Config
├── docker-compose.yml                     # MySQL Container Configuration
└── pom.xml                                # Maven Dependencies
```

## 🚀 Key Implementation Details

### 1. Object-Relational Mapping (ORM)
The application eliminates "SQL in Java strings." Instead, we use **JPA Annotations** inside `User.java` to map objects directly to the database:
* **@Entity**: Marks the class as a persistent database table.
* **@Id & @GeneratedValue**: Handles the primary key and automatic ID incrementing.
* **@Column**: Maps specific Java fields to table columns.



### 2. Hibernate Session & Transaction Management
Unlike JDBC's `Statement`, Hibernate uses a **Session** to manage the lifecycle of an object:

| Method | Purpose | Standard |
| :--- | :--- | :--- |
| **`session.persist()`** | Saves a new entity to the database. | JPA Standard (Replaces `save`) |
| **`session.merge()`** | Updates an existing entity. | JPA Standard (Replaces `update`) |
| **`session.remove()`** | Deletes an entity from the database. | JPA Standard (Replaces `delete`) |
| **`session.get()`** | Retrieves an entity by its Primary Key. | Hibernate/JPA |

### 3. HQL (Hibernate Query Language)
To fetch all users, we no longer write `SELECT * FROM users`. We use **HQL**, which is object-oriented and queries the class name rather than the table:

```java
// Logic inside UserDAO:
return session.createQuery("from User", User.class).list();
```

This makes the application **database-independent**; Hibernate translates this HQL into the correct SQL dialect (MySQL, PostgreSQL, etc.) automatically.

---

## ⚙️ Setup Instructions (Ubuntu)

### 1. Database Setup
Ensure your MySQL container is running:
```bash
  docker-compose up -d
```

Verify the container status:

```bash
  docker ps
```
### 2. Hibernate Configuration
Ensure `src/main/resources/hibernate.cfg.xml` is configured with:

* **`hbm2ddl.auto`**: Set to `update` to allow Hibernate to create or update your tables automatically based on your Entity classes.
* **`dialect`**: Set to `org.hibernate.dialect.MySQLDialect` so Hibernate knows how to generate the correct SQL for your MySQL version.



### 3. IntelliJ & Running
1. Set **Deployment Directory** to `src/main/webapp`.
2. Set **Context Path** to `/`.
3. Start Tomcat and visit `http://localhost:8080`.

---

## 📝 Learning Notes

* **Boilerplate Reduction**: Hibernate removes the need for `ResultSet` iterating and manual object mapping. You no longer have to manually map database columns to Java fields.
* **Type Safety**: By using `User.class` in queries, we get better compile-time checks and IDE auto-completion compared to raw SQL strings.
* **Abstraction**: The DAO logic is now focused on **what** to do with the data (save, delete, update), not **how** to write the specific SQL syntax for it.