# Hibernate Implementation Guide

We will stop writing SQL strings like "INSERT INTO users..." and instead let Hibernate handle the database interaction by mapping your Java Objects (Entities) directly to Database Tables.

To get started with Hibernate in your existing project, we need to update three main areas:

## 1. Update pom.xml
We need the Hibernate Core engine and the Jakarta Persistence API (JPA). Replace or add these dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.4.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
</dependencies>
```

## 2. Create hibernate.cfg.xml

Instead of hardcoding credentials in a Java file (`UserDAO`), Hibernate uses an XML configuration file located in `src/main/resources`. This file manages the database connection pool and Hibernate-specific settings.


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "[http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd](http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd)">
<hibernate-configuration>
    <session-factory>
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/user_db?useSSL=false</property>
        <property name="connection.username">root</property>
        <property name="connection.password">password</property>

        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        <property name="show_sql">true</property>
        <property name="format_sql">true</property>

        <property name="hbm2ddl.auto">update</property>

        <mapping class="com.sachin.model.User"/>
    </session-factory>
</hibernate-configuration>
```

## 3. Annotate your User.java (The Entity)

We tell Hibernate that this class is a table using the `@Entity` annotation. This creates a link between the Java class and a database table.

```java
package com.sachin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    // Hibernate requires a no-arg constructor
    public User() {}

    // Getters and Setters...
}
```

## Transitioning UserDAO to Hibernate

To transition your `UserDAO` to Hibernate, we need to replace the manual JDBC Connection logic with a Hibernate **SessionFactory**. Instead of opening a connection, we open a **Session**, which acts as a "conversation" between your Java application and the database.



### 1. Create a Hibernate Utility Class
First, we need a utility class to handle the `SessionFactory`. This is an expensive object to create, so we use a Singleton pattern to ensure we only want one for the whole app.

Create **`src/main/java/com/sachin/util/HibernateUtil.java`**:

```java
package com.sachin.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.sachin.model.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // This automatically looks for hibernate.cfg.xml
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
```

### 2. Rewrite the UserDAO.java using Hibernate
Notice how we no longer write SQL strings. We use `session.persist()`, `session.remove()`, and **HQL (Hibernate Query Language)**.

```java
package com.sachin.dao;

import com.sachin.model.User;
import com.sachin.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDAO {

    // 1. SAVE USER
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user); // Hibernate handles the INSERT SQL
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // 2. GET ALL USERS
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL uses Class names (User) not table names (users)
            return session.createQuery("from User", User.class).list();
        }
    }

    // 3. UPDATE USER
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user); // Hibernate handles the UPDATE SQL
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // 4. DELETE USER
    public void deleteUser(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user); // Hibernate handles the DELETE SQL
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
```

## Why this is a huge upgrade:

* **No Boilerplate**: No more `PreparedStatement`, `ResultSet`, or mapping rows to objects manually. Hibernate does the mapping for you.
* **HQL over SQL**: You write queries against your Java Classes (`from User`), making your code database-independent.
* **Automatic Table Creation**: In your `hibernate.cfg.xml`, if you set `hbm2ddl.auto` to `update`, Hibernate will look at your `@Entity` class and create the table for you if it doesn't exist.
