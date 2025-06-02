package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * UserController handles user registration, login, and access to the download page.
 *
 * This controller provides endpoints for displaying the signup and login forms,
 * processing new user registrations, and directing users to the download page after successful signup.
 *
 * Endpoints:
 * - GET /signup    : Displays the signup form.
 * - POST /signup   : Processes new user registration. Adds user if email doesn't already exist.
 * - GET /login     : Displays the login form.
 * - GET /download  : Displays the download page after successful signup.
 *
 * Dependencies:
 * - UserService: for business logic related to user registration and email checking.
 */
@Controller
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "signup";
        }

        userService.addUser(user.getName(), user.getEmail(), user.getPassword(), "Carer");
        return "redirect:/download";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/download")
    public String showDownloadPage() {
        return "download";
    }
}