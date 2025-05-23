package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
