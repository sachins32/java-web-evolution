package com.sachin.dao;

import com.sachin.model.User;
import com.sachin.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UserDAO {

    // Save User
    public void saveUser(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();;
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Get All Users
    public List<User> getAllUsers() {
        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // JPQL
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    // Update User
    public void updateUser(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Delete User
    public void removeUser(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
