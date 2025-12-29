# 🚀 User Management System: Servlet to Spring MVC Migration

This repository documents the architectural evolution of a Java Web Application. We transitioned from a manual **Jakarta Servlet & JPA** implementation to a modernized **Spring MVC 6** architecture with **Spring ORM**.

---

## 📌 Project Overview
The goal of this project was to eliminate boilerplate code, implement a proper **MVC (Model-View-Controller)** pattern, and leverage Spring's **Inversion of Control (IoC)** and **Declarative Transaction Management**.

---

## 🛠 Tech Stack
* **Java:** 17+
* **Framework:** Spring Framework 6.1 (MVC, ORM, Context)
* **ORM Provider:** Hibernate 6.x
* **Database:** MySQL 8.0
* **Server:** Apache Tomcat 10.1.x (Jakarta Namespace)
* **Build Tool:** Maven 3.6+

---

## 🏗 Key Architectural Changes

### 1. From XML to "Zero-XML" Configuration
We eliminated `web.xml` and `persistence.xml` in favor of Java-based configuration:
* **`WebAppInitializer.java`**: Replaces `web.xml`. Configures the `DispatcherServlet`.
* **`AppConfig.java`**: Replaces `persistence.xml`. Configures the `DataSource`, `LocalContainerEntityManagerFactory`, and `InternalResourceViewResolver`.



### 2. Layered Component Architecture
| Layer | Technology | Responsibility |
| :--- | :--- | :--- |
| **Model** | JPA Entities (`@Entity`) | Defines the data structure and database mapping. |
| **Data Access** | `@Repository` (DAO) | Handles CRUD using `@PersistenceContext EntityManager`. |
| **Controller** | `@Controller` | Manages request mapping (`@GetMapping`/`@PostMapping`) and UI flow. |
| **View** | JSP with JSTL 3.0 | Modernized, card-based UI inside `WEB-INF/views`. |

---

## 🚀 Migration Steps

### Step 1: Maven Modernization
We replaced standalone Jakarta dependencies with the Spring 6 stack:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>6.1.x</version>
    </dependency>
    
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-orm</artifactId>
        <version>6.1.x</version>
    </dependency>
    
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.x</version>
    </dependency>
</dependencies>
```

### Step 2: Persistence Logic Refactoring

A major highlight of this migration was moving away from manual transaction management. We replaced the verbose `begin()` and `commit()` blocks with Spring's **Declarative Transaction Management**.

#### 🛠 Implementation in `UserDAO.java`
By using the `@Repository` and `@Transactional` annotations, we shifted the responsibility of session and transaction handling to the Spring Container.

```java
@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager em; // Spring injects the EntityManager automatically

    @Transactional
    public void saveOrUpdateUser(User user) {
        if (user.getId() == 0) {
            // New user: Perform persist
            em.persist(user);
        } else {
            // Existing user: Perform merge (update)
            em.merge(user);
        }
    }
    
    @Transactional
    public void deleteUser(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}
```

### Step 3: UI Enhancement
We moved away from the default, plain HTML structure to a modern, responsive design using custom CSS. This transition focused on making the administrative interface intuitive and professional.

* **Responsive Data Tables:** Implemented a card-based container for the user list with **hover effects** on rows to improve scannability.
* **Centered Card Layouts:** Form pages (Add/Edit) were refactored into centered cards with clear typography and stylized input fields.
* **User Experience (UX) Safety:** Added JavaScript-based **safety dialogs** (confirmation pop-ups) for delete actions to prevent accidental data loss.



---

### ⚠️ Challenges & Resolutions (Interview Insights)

During the migration, we encountered specific technical hurdles that provided deep insights into the inner workings of Spring MVC and Jakarta EE.

#### 1. The "Duplicate Entry" Bug
* **Issue:** When attempting to update an existing user, the system threw a MySQL `Duplicate entry` error for the email field.
* **Root Cause:** Two-fold. First, the JSP form was missing a `<form:hidden path="id" />` field, meaning the ID wasn't sent back to the server. Second, the `User.java` entity had a broken `setId` method. This caused Spring to see the ID as `0`, leading Hibernate to treat the request as a **New Insert** rather than an **Update**.
* **Resolution:** 1. Added a hidden path for the ID in the JSP to maintain state.
    2. Corrected the Java setter method signature to ensure proper data binding.

#### 2. JSTL URI Conflicts (Tomcat 10 Migration)
* **Issue:** The application failed to render JSPs, throwing: `The absolute uri: [http://jakarta.tags.core] cannot be resolved`.
* **Cause:** Tomcat 10+ uses Jakarta EE 10, which updated the JSTL specification. The traditional `http://` prefix in the URI was deprecated in favor of the direct namespace.
* **Resolution:** Updated the taglib directive across all JSP files to:
  `<%@ taglib uri="jakarta.tags.core" prefix="c" %>`

---

### 🏁 Final Summary
By migrating from a manual Servlet approach to Spring MVC, the application achieved:

* **60% Reduction in Boilerplate:** Declarative transactions and auto-configuration removed the need for manual resource management.
* **Type Safety:** Achieved automatic binding of form data to Java objects via `@ModelAttribute`, reducing manual parsing errors.
* **Scalability:** The infrastructure is now highly modular and ready for **Spring Data JPA** or a **REST API** transition.

---

### 📖 How to Run
1.  **Clone** the repository and switch to the `ftr_spring_mvc` branch.
2.  Ensure your **MySQL** instance is running (via Docker-Compose or local installation).
3.  Run `mvn clean install` to build the `.war` file.
4.  Deploy the generated artifact to **Tomcat 10.1+**.
5.  Access the application at: `http://localhost:8080/UserManagementApp/`