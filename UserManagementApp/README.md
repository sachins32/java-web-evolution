# User Management App (Servlet, JPA Standard, MySQL and Docker)

A robust Java Web Application built using **Jakarta Servlet API** and **Jakarta Persistence API (JPA) 3.0**. This version migrates the application from Hibernate-specific APIs to the standardized **JPA Specification**. By using the `EntityManager`, the application becomes provider-agnostic while still leveraging **Hibernate 6** as the underlying persistence provider.



---

## 🚀 Features
* **Standardized Persistence**: Uses `EntityManager` and `EntityTransaction` instead of Hibernate-specific Sessions.
* **Welcome Page**: A dynamic JSP interface to interact with the application.
* **Create User (POST)**: Persists user entities using the standardized `em.persist()`.
* **View Users (GET)**: Fetches data using **JPQL (Java Persistence Query Language)**.
* **Update User (PUT)**: Synchronizes modified Java objects to the database using `em.merge()`.
* **Delete User (DELETE)**: Removes persistent entities via `em.remove()`.
* **Standardized Configuration**: Centralized management via `persistence.xml` in the `META-INF` directory.
* **Automatic Schema Generation**: Managed via the `jakarta.persistence.schema-generation` property.

---

## 🛠 Tech Stack
* **Language**: Java 17+
* **Specification**: Jakarta Persistence API (JPA) 3.1
* **ORM Provider**: Hibernate 6.x (Implementation Provider)
* **Database**: MySQL 8.0
* **Specification**: Jakarta EE 10 (Servlet API 6.0, JSTL 3.0)
* **Design Pattern**: DAO (Data Access Object) & Singleton (JPAUtil)
* **Server**: Apache Tomcat 10.1.x
* **Build Tool**: Maven

---

## 📂 Project Structure

In the `ftr_servlet_jpa` branch, the project follows the standard Maven directory layout with the addition of the mandatory `META-INF` folder for JPA configuration.



```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── model/
│   │   │       │   └── User.java          # Entity with JPA Annotations
│   │   │       ├── dao/
│   │   │       │   └── UserDAO.java       # JPA EntityManager CRUD implementation
│   │   │       ├── util/
│   │   │       │   └── JPAUtil.java       # EntityManagerFactory Singleton
│   │   │       └── servlet/
│   │   │           └── UserServlet.java   # Request Routing & Controller
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml        # Standard JPA Configuration (Mandatory)
├── docker-compose.yml                     # MySQL 8.0 Container Configuration
└── pom.xml                                # Maven dependencies (Hibernate 6 + Servlet 6)
```

## 🚀 Key Implementation Details

### 1. The Standardized Entry Point (persistence.xml)
JPA shifts configuration from `hibernate.cfg.xml` to a standardized `persistence.xml`. This file defines the **Persistence Unit**, which acts as the core configuration for database connectivity and provider settings.



### 2. EntityManager & Transaction Management
We replace the Hibernate Session with the JPA **EntityManager**. This is the standard "unit of work" for Jakarta EE applications.

| Method | Purpose | Specification |
| :--- | :--- | :--- |
| **`em.persist()`** | Makes an instance managed and persistent. | Jakarta Persistence |
| **`em.merge()`** | Merges the state of the given entity into the current persistence context. | Jakarta Persistence |
| **`em.remove()`** | Removes the entity instance. | Jakarta Persistence |
| **`em.find()`** | Finds by primary key. (Replaces `session.get`) | Jakarta Persistence |



### 3. JPQL (Java Persistence Query Language)
We use **JPQL**, the standardized version of HQL. While similar, JPQL strictly follows the Jakarta specification, ensuring that queries like `SELECT u FROM User u` remain consistent across different ORM providers.

```java
// Logic inside UserDAO using EntityManager:
return em.createQuery("SELECT u FROM User u", User.class).getResultList();
```

### 2. JPA Configuration
The configuration file **must** be placed in `src/main/resources/META-INF/persistence.xml`. Key properties include:

* **`jakarta.persistence.schema-generation.database.action`**: Set to `update` to manage schema automatically.
* **`hibernate.dialect`**: Still required to tell the provider (Hibernate) how to talk to MySQL.



---

## 📝 Learning Notes

* **Specification vs Implementation**: By using JPA (`jakarta.persistence.*`), we write code against a standard interface. Hibernate is simply the engine under the hood.
* **Explicit Management**: The `EntityManager` lifecycle is strictly defined. We use `JPAUtil` to provide a single `EntityManagerFactory`, which is a heavy-weight object used to spawn `EntityManager` instances for each request.
* **Future-Proofing**: This migration makes the app "Cloud Native" ready, as most enterprise Java frameworks (like Spring Data JPA or Quarkus) rely on the JPA standard rather than direct Hibernate calls.