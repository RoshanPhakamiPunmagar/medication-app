package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication.getName(); // email as username
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            model.addAttribute("name", user.getName());
            model.addAttribute("role", user.getRoleId().toString()); // match with HTML check
        }

        return "dashboard";
    }
}
