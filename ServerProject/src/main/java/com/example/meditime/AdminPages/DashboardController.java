package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * DashboardController manages the display of the user dashboard page.
 *
 * This controller retrieves the currently authenticated user's details based on their email,
 * maps their role ID to a role name, and passes relevant information (name and role) to the
 * dashboard view.
 *
 * Endpoint:
 * - GET /dashboard : Displays the dashboard page with the user's name and role.
 *
 * Dependencies:
 * - UserRepository: to retrieve user data from the database.
 * - Spring Security Authentication: to identify the currently logged-in user.
 */
@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String email = authentication.getName(); // Get the logged-in user's email

        // Fetch user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Map roleId to role name if necessary
        String roleName = switch (user.getRoleId().intValue()) {
            case 1 -> "ADMIN";
            case 2 -> "CARER";
            case 3 -> "CLIENT";
            default -> "UNKNOWN";
        };

        // Add attributes to the model
        model.addAttribute("name", user.getName());
        model.addAttribute("role", roleName);

        return "dashboard";
    }
}