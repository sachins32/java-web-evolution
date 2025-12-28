package com.sachin.controller;

import com.sachin.dao.UserDAO;
import com.sachin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userDAO.getAllUsers());
        return "displayUsers"; // Points to displayUsers.jsp
    }

    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        System.out.println("id is:" + user.getId());
        userDAO.saveOrUpdateUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("userId") int id, Model model) {
        model.addAttribute("user", userDAO.getUserById(id));
        return "userForm";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int id) {
        userDAO.deleteUser(id);
        return "redirect:/users/list";
    }
}