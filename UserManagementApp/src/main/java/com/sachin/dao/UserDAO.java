package com.sachin.dao;

import com.sachin.model.User;
import com.sachin.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {

    // Save User
    public void saveUser(User user) {
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(user); // Hibernate handles the INSERT SQL
            // session.save(); this is deprecated
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    // Get All Users
    public List<User> getAllUsers() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL uses Class names (User) not table names (users)
            return session.createQuery("from User", User.class).list();
        }
    }

    // Update User
    public void updateUser(User user) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user); // Hibernate handles the UPDATE SQL
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete User
    public void removeUser(int id) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user); // Hibernate handles the DELETE SQL
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

}
