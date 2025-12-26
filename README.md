# java-web-evolution
A comprehensive step-by-step evolution of a Java web application, tracing the journey from basic Servlets and JSP to modern Spring Boot 3.x with Spring Data JPA. This project demonstrates how abstraction layers reduce boilerplate code in enterprise Java development.

---

## 🚀 Branch Guide

This project is organized into specific branches to show the evolution of building the java web apps. 

| Branch Name | Tech Stack | Data Storage | Learning Milestone |
| :--- | :--- | :--- | :--- |
| `ftr_servlets` | Servlets + JSP | ArrayList | Request/Response, JSP tags, & State management. |
| `ftr_sevlet_jdbc_mysql` | Servlets + JDBC | MySQL | Connection strings, SQL statements, & ResultSets. |
| `ftr_servlet_orm_hibernate` | Servlets + Hibernate | MySQL | Native Hibernate Session management (The pioneer of ORM). |
| `ftr_servlet_jpa` | Servlets + Jakarta JPA | MySQL | Transitioning to the Java Standard (JPA Specification). |
| `ftr_spring_mvc_hibernate` | Spring MVC + Hibernate | MySQL | Dependency Injection & DispatcherServlet. |
| `ftr_spring_mvc_jpa` | Spring MVC + JPA | MySQL |
| `ftr_springboot_hibernate` | Spring Boot + Hibernate | MySQL | Fat JARs & Auto-configuration. |
| `ftr_springboot_jpa` | Spring Boot + JPA | MySQL | Standardizing ORM within the Spring ecosystem. |
| `ftr_springboot_spring_data_jpa` | Spring Boot + Data JPA | MySQL | Repository Abstraction & zero-boilerplate CRUD. |
| `ftr_springboot_rest_api` | Spring Boot + REST | MySQL | JSON responses & API design. |

---

## 🛠️ How to Navigate

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/java-web-evolution.git](https://github.com/YOUR_USERNAME/java-web-evolution.git)

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
