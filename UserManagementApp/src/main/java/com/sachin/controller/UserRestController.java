package com.sachin.controller;

import com.sachin.model.User;
import com.sachin.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserDAO userDAO;

    // 1. GET - Retrieve all users
    @GetMapping("/users")
    public List<User> getUsers() {
        return userDAO.getAllUsers(); // Automatically converted to JSON Array
    }

    // 2. GET - Retrieve a single user by ID
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable("userId") int userId) {
        return userDAO.getUserById(userId);
    }

    // 3. POST - Create a new user
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        // Force ID to 0 to ensure an INSERT happens
        user.setId(0);
        userDAO.saveOrUpdateUser(user);
        return user;
    }

    // 4. PUT - Update an existing user
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userDAO.saveOrUpdateUser(user);
        return user;
    }

    // 5. DELETE - Remove a user
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userDAO.deleteUser(userId);
        return "Deleted user id - " + userId;
    }
}