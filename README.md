# ☕ Java Web Evolution

**Description:** A comprehensive journey from basic **Servlets/JSP** to modern **Spring Boot 3.x REST APIs**. This project demonstrates how abstraction layers reduce boilerplate code and improve maintainability in enterprise Java development.

---

## 🛠️ Evolutionary Stages (Branch Guide)

This project is structured into branches. Each branch represents a "Level Up" in technology.

| Branch Name | Tech Stack | Storage | Learning Milestone |
| :--- | :--- | :--- | :--- |
| 🟢 `ftr_servlets` | **Servlets + JSP** | `ArrayList` | Request/Response lifecycle & JSP tags. |
| 🟣 `ftr_servlets_jstl` | **Servlets + JSTL** | `ArrayList` | JSTL core tags, formatting, and avoiding Scriptlets. |
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

## 🛠️ Prerequisites

To run the applications in this repository, ensure you have the following installed:

* **JDK:** 17 or higher
* **Build Tool:** Maven 3.8+
* **Database:** MySQL 8.0+ (Required for JDBC, Hibernate, and Spring branches)
* **Server:** Apache Tomcat 10+ (Required for non-Spring Boot branches to support the `jakarta.*` namespace)

---

## 📝 Key Observations

Throughout this project, pay close attention to the following architectural shifts:

> 💡 **Frontend Shift:** Observe the transition from **Server-Side Rendering (JSP)** to a modern, **Stateless REST API** that communicates via JSON.
>
> 💡 **Boilerplate Reduction:** Compare the complexity of the manual DAO implementation in the `ftr_sevlet_jdbc_mysql` branch against the 1-line Repository interface in `ftr_springboot_spring_data_jpa`.
>
> 💡 **Configuration Evolution:** Watch the movement away from verbose `web.xml` (XML-based) files toward **Annotation-based** configuration, and finally to **Auto-configuration** via `application.properties`.
