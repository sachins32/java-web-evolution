# User Management App (Spring MVC, JPA, MySQL and Docker)

A modern Java Web Application migrated to the **Spring Framework 6**. This version replaces the manual boilerplate of Jakarta Servlets with **Spring MVC** and utilizes **Spring ORM** to manage the Jakarta Persistence API (JPA) lifecycle. The application is now fully configured via Java (**Zero-XML**), removing the need for `web.xml` and `persistence.xml`.



---

## 🚀 Features
* **Zero-XML Configuration**: Fully Java-based configuration using `@Configuration`, `@EnableWebMvc`, and `AbstractAnnotationConfigDispatcherServletInitializer`.
* **Annotation-Driven Development**: Uses `@Controller`, `@Service`, and `@Repository` for a clean, layered architecture.
* **Spring Managed Transactions**: Replaces manual `em.getTransaction().begin()` with the declarative `@Transactional` annotation.
* **Unified Data Binding**: Leverages `@ModelAttribute` and Spring Form Tags for automatic mapping of HTML form data to Java objects (POJOs).
* **Centralized View Management**: Uses `InternalResourceViewResolver` to securely manage JSPs within the `WEB-INF/views/` directory.
* **Declarative Persistence**: Database connection and `EntityManagerFactory` are managed as Spring Beans in `AppConfig.java`.

---

## 🛠 Tech Stack
* **Framework**: Spring Framework 6.x (Spring MVC, Spring ORM, Spring TX)
* **Specification**: Jakarta Persistence API (JPA) 3.1
* **ORM Provider**: Hibernate 6.x
* **Database**: MySQL 8.0
* **View Technology**: JSP (Jakarta Standard Tag Library 3.0)
* **Server**: Apache Tomcat 10.1.x
* **Build Tool**: Maven

---

## 📂 Project Structure

This branch follows the "Zero-XML" approach. Configuration files previously located in `META-INF` or `WEB-INF` have been moved to Java classes.

```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── config/
│   │   │       │   ├── AppConfig.java          # Database & View Config (Replaces persistence.xml)
│   │   │       │   └── WebAppInitializer.java    # Servlet Config (Replaces web.xml)
│   │   │       ├── controller/
│   │   │       │   └── UserController.java     # Spring MVC Controller (Replaces UserServlet)
│   │   │       ├── dao/
│   │   │       │   └── UserDAO.java            # @Repository with @PersistenceContext
│   │   │       └── model/
│   │   │           └── User.java               # JPA Entity
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   │               ├── displayUsers.jsp        # Modernized CSS User List
│   │               └── userForm.jsp            # Dynamic Spring Form
├── docker-compose.yml                          # MySQL 8.0 Container
└── pom.xml                                     # Spring 6 + Hibernate 6 Dependencies
```

## 🚀 Key Implementation Details

### 1. Dependency Injection & Persistence
In the previous architecture, we relied on a manual `JPAUtil` singleton to manage the `EntityManagerFactory`. In this Spring-based version, we utilize **Dependency Injection**. Spring injects the `EntityManager` directly into the DAO using the `@PersistenceContext` annotation. This decouples the DAO from the lifecycle management and ensures thread-safe operations within the persistence context.



### 2. Transaction Management
Manual transaction handling (`begin`, `commit`, `rollback`) has been entirely removed from the business logic. By adding `@EnableTransactionManagement` to the configuration and annotating DAO methods with **`@Transactional`**, Spring handles the transaction lifecycle via AOP (Aspect-Oriented Programming).

| Operation | Manual JPA (Previous) | Spring MVC (Current) |
| :--- | :--- | :--- |
| **Start Transaction** | `em.getTransaction().begin()` | **`@Transactional`** |
| **Persist Data** | `em.persist(user)` | `em.persist(user)` |
| **End Transaction** | `em.getTransaction().commit()` | **(Automatic)** |



### 3. Data Binding & Form Handling
Using Spring’s `<form:form>` tag library enables **two-way data binding**. This is a significant improvement over manual request parameter parsing. It ensures that the `id` of a user is correctly preserved during an edit operation via a hidden field, which prevents Hibernate from attempting a new insert (and thus preventing `Duplicate Entry` errors).

```jsp
<%-- Spring Form Tag Library Implementation --%>
<form:form action="save" modelAttribute="user" method="POST">
    <form:hidden path="id" /> 
    
    <label>User Name:</label>
    <form:input path="name" />
    
    <form:button>Submit</form:button>
</form:form>
```

## 📝 Learning Notes

This migration provided key insights into how modern frameworks manage application complexity through design patterns and standardized namespaces.

* **Inversion of Control (IoC):** The developer is no longer responsible for the manual instantiation of `UserDAO` or the `EntityManager`. Spring’s IoC container manages the entire object lifecycle—creation, dependency injection, and destruction.
* **Front Controller Pattern:** The `DispatcherServlet` serves as the centralized entry point for all requests. It eliminates the need for multiple Servlets by routing traffic to specific methods in the `UserController` based on `@GetMapping` or `@PostMapping` annotations.



* **Jakarta Namespace:** To ensure compatibility with **Tomcat 10+**, the project fully utilizes the `jakarta.*` namespace for Servlets, Persistence (JPA), and JSTL tags, successfully moving away from the legacy `javax.*` namespace.
* **Data Integrity:** This branch highlighted the critical role of **JavaBean naming conventions**. Proper setter methods (e.g., `setId`) are essential for Spring's `DataBinder` to correctly map HTML form data back to persistent database entities during update operations.

---

## 📖 How to Run

Follow these steps to deploy the application locally:

### 1. Start the Database
Ensure Docker is running and start the MySQL container:
```bash
  docker-compose up -d
```