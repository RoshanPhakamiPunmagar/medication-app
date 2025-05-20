package com.example.meditime.AdminPages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/support")
public class CarerController {

    @GetMapping("/dashboard")
    public String carerDashboard(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "carer-dashboard"; // maps to carer-dashboard.html
    }
}
