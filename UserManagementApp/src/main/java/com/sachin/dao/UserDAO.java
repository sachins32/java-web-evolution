package com.sachin.dao;

import com.sachin.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    public void saveOrUpdateUser(User user) {
        if (user.getId() == 0) {
            // New User: Use persist
            em.persist(user);
        } else {
            // Existing User: Use merge
            em.merge(user);
        }
    }

    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    public void deleteUser(int id) {
        User u = em.find(User.class, id);
        if (u != null) em.remove(u);
    }
}