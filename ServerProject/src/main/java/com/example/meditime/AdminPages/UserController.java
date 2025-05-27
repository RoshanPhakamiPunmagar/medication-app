package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class responsible for handling user-related pages
 * such as signup, login, and access to the app download page.
 */
@Controller
public class UserController {

    // Injecting the UserService to handle business logic related to users
    @Autowired
    private UserService userService;

    /**
     * Displays the signup form when users access /signup via GET.
     * Adds an empty User object to the model for form binding.
     */
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // Returns the signup.html template
    }

    /**
     * Processes the signup form submission via POST.
     * Checks if the email already exists; if not, adds the user with the role "Carer".
     */
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, Model model) {
        // Check if a user with the given email already exists
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "signup"; // Return the same form with error
        }

        // Add new user to the database with role "Carer"
        userService.addUser(user.getName(), user.getEmail(), user.getPassword(), "Carer");

        // Redirect user to download page after successful signup
        return "redirect:/download";
    }

    /**
     * Displays the login form at /login.
     * Adds an empty User object to the model for form binding.
     */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // Returns the login.html template
    }

    /**
     * Displays the download page at /download.
     */
    @GetMapping("/download")
    public String showDownloadPage() {
        return "download"; // Returns the download.html template
    }

    @GetMapping("/faq")
    public String showFaqPage() {
        return "faq"; // Returns the download.html template
    }
}
