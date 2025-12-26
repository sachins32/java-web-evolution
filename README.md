# ☕ Java Web Evolution

**Description:** A comprehensive journey from basic **Servlets/JSP** to modern **Spring Boot 3.x REST APIs**. This project demonstrates how abstraction layers reduce boilerplate code and improve maintainability in enterprise Java development.

---

## 🛠️ Evolutionary Stages (Branch Guide)

This project is structured into branches. Each branch represents a "Level Up" in technology.

| Branch Name | Tech Stack | Storage | Learning Milestone |
| :--- | :--- | :--- | :--- |
| 🟢 `ftr_servlets` | **Servlets + JSP** | `ArrayList` | Request/Response lifecycle & JSP tags. |
| 🟢 `ftr_sevlet_jdbc_mysql` | **Servlets + JDBC** | `MySQL` | Manual SQL queries & Connection pooling. |
| 🔵 `ftr_servlet_orm_hibernate` | **Servlets + Hibernate** | `MySQL` | Native Hibernate `SessionFactory` & `Session`. |
| 🔵 `ftr_servlet_jpa` | **Servlets + Jakarta JPA** | `MySQL` | Standardized `EntityManager` & `persistence.xml`. |
| 🟡 `ftr_spring_mvc_hibernate` | **Spring MVC + Hibernate** | `MySQL` | Dependency Injection & `DispatcherServlet`. |
| 🟡 `ftr_spring_mvc_jpa` | **Spring MVC + JPA** | `MySQL` | Pure JPA implementation within Spring MVC. |
| 🟠 `ftr_springboot_hibernate` | **Spring Boot + Hibernate**| `MySQL` | Auto-configuration & Embedded Tomcat. |
| 🟠 `ftr_springboot_jpa` | **Spring Boot + JPA** | `MySQL` | JPA standardization in the Spring container. |
| 🔴 `ftr_springboot_spring_data_jpa` | **Spring Boot + Data JPA**| `MySQL` | Repository interfaces & Zero-boilerplate CRUD. |
| 🟣 `ftr_springboot_rest_api` | **Spring Boot + REST** | `MySQL` | Stateless JSON APIs & Postman testing. |

---

## 🛠️ How to Navigate

1. **Clone the repository:**
   ```bash
   git clone https://github.com/sachins32/java-web-evolution.git

2. **Explore a specific stage: Check out the branch you are interested in to see the code changes:**
   ```bash
   git checkout <branch_name>

3. **Prerequisites:**
   JDK: 17 or higher
   Build Tool: Maven
   Database: MySQL (Required for JDBC and Hibernate branches)
   Server: Apache Tomcat 9+ (Required for non-Spring Boot branches)

4. **Key Observations:**
   Frontend Shift: Transition from Server-Side Rendering (JSP) to a Stateless REST API.
   Boilerplate Reduction: Compare the DAO implementation in the JDBC branch vs the Spring Data JPA branch.
   Configuration Evolution: Transition from web.xml (XML-based) to Annotation-based and finally to application.properties auto-configuration.
