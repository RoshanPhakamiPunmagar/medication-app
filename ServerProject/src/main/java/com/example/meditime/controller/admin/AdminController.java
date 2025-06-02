package com.example.meditime.controller.admin;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * AdminController provides administrative functionality for managing users.
 *
 * This controller allows an admin to list all users, add new users, edit existing user details,
 * and delete users. It handles password encoding for new and updated users and ensures that
 * existing passwords are preserved if not explicitly changed.
 *
 * Endpoints:
 * - GET  /admin/users           : Displays a list of all users.
 * - GET  /admin/users/add       : Shows the form to add a new user.
 * - POST /admin/users/save      : Saves a new or updated user (with password hashing and role handling).
 * - GET  /admin/users/edit/{id} : Displays the form to edit an existing user.
 * - GET  /admin/users/delete/{id} : Deletes the user with the specified ID.
 *
 * Dependencies:
 * - UserRepository: to access user data from the database.
 * - PasswordEncoder: to securely hash user passwords.
 */
@Controller
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        if (user.getUserId() == null) {
            // New user — hash password and assign default roleId = 2
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoleId(2L); // Set roleId to 2 for new users
        } else {
            // Existing user — update password only if not blank
            User existing = userRepository.findById(user.getUserId()).orElseThrow();
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existing.getPassword());
            }

            // Keep the original roleId if not explicitly updated
            if (user.getRoleId() == null) {
                user.setRoleId(existing.getRoleId());
            }
        }

        userRepository.save(user);
        return "redirect:/admin/users";
    }


    private boolean isPasswordChanged(User user) {
        return userRepository.findById(user.getUserId())
                .map(existing -> !passwordEncoder.matches(user.getPassword(), existing.getPassword()))
                .orElse(true);
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        // To avoid displaying hashed password in form
        user.setPassword("");
        model.addAttribute("user", user);
        return "user-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}