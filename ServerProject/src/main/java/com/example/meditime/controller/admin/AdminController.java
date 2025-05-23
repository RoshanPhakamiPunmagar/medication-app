package com.example.meditime.controller.admin;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

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
            // New user — always hash password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // Existing user — update password only if not blank
            User existing = userRepository.findById(user.getUserId()).orElseThrow();
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existing.getPassword());
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
