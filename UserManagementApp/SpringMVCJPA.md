# JPA Implementation Guide

## 1. Project Configuration (persistence.xml)

We have standardized our ORM implementation by moving from Hibernate-specific APIs (`Session`/`SessionFactory`) to the **Jakarta Persistence API (JPA)** (`EntityManager`/`EntityManagerFactory`). This ensures our code is specification-compliant and portable.

JPA uses a standardized configuration file located at `src/main/resources/META-INF/persistence.xml`. This replaces `hibernate.cfg.xml`.



```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="[https://jakarta.ee/xml/ns/persistence](https://jakarta.ee/xml/ns/persistence)"
             xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
             xsi:schemaLocation="[https://jakarta.ee/xml/ns/persistence](https://jakarta.ee/xml/ns/persistence) [https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd](https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd)"
             version="3.0">

    <persistence-unit name="user-management-unit">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>com.sachin.model.User</class>

        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/user_db?useSSL=false"/>
            <property name="jakarta.persistence.jdbc.user" value="root"/>
            <property name="jakarta.persistence.jdbc.password" value="password"/>

            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="jakarta.persistence.schema-generation.database.action" value="update"/>
        </properties>
    </persistence-unit>
</persistence>
```

## 2. JPA Utility Class (JPAUtil.java)

We replaced `HibernateUtil` with `JPAUtil`. Instead of a `SessionFactory`, we now manage an **EntityManagerFactory**. This factory is a thread-safe, heavyweight object that should be created once for the entire application.



```java
package com.sachin.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            // "user-management-unit" must match the name in persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("user-management-unit");
        }
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
```

## 3. Standardized DAO (UserDAO.java)

The `UserDAO` now uses **EntityManager** for all operations. Notice the shift from `Session` to `EntityManager` and `Transaction` to `EntityTransaction`. Each method manages its own lifecycle by opening and closing the `EntityManager` to prevent memory leaks.



```java
package com.sachin.dao;

import com.sachin.model.User;
import com.sachin.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class UserDAO {

    // 1. SAVE USER
    public void saveUser(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // 2. GET ALL USERS
    public List<User> getAllUsers() {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // JPQL syntax: SELECT u FROM User u
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    // 3. UPDATE USER
    public void updateUser(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // 4. DELETE USER
    public void deleteUser(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id); // find() replaces get()
            if (user != null) {
                em.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
```

## 🚀 Why we migrated to JPA

By migrating to JPA, we move from a vendor-specific implementation to a standard Java specification. This makes the application more maintainable and aligned with modern Java EE/Jakarta EE standards.

| Feature | Hibernate Legacy | JPA Standard |
| :--- | :--- | :--- |
| **Main Factory** | `SessionFactory` | `EntityManagerFactory` |
| **Working Object** | `Session` | `EntityManager` |
| **Transaction** | `Transaction` | `EntityTransaction` |
| **Query Language** | HQL | JPQL (Standard) |
| **Portability** | Locked to Hibernate | Works with EclipseLink, OpenJPA, etc. |


---

## 📝 Key Learnings

* **Specification over Implementation**: We now write code against the `jakarta.persistence` interfaces rather than Hibernate-specific classes. This decouples our business logic from the underlying ORM provider.
* **Manual Closing**: Unlike the Hibernate "openSession" try-with-resources (which some configurations handle automatically), the `EntityManager` should be explicitly closed in a `finally` block (or handled by the container in managed environments) to avoid database connection leaks.