package com.example.meditime.controller.admin;

import com.example.meditime.model.Client;
import com.example.meditime.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clients")
public class AdminClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "client-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Client client) {
        clientRepository.save(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElseThrow();
        model.addAttribute("client", client);
        return "client-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/admin/clients";
    }
}
