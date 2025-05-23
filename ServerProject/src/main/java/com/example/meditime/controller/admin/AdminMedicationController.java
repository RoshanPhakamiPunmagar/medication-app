package com.example.meditime.controller.admin;

import com.example.meditime.model.Medication;
import com.example.meditime.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/medications")
public class AdminMedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping
    public String listMedications(Model model) {
        model.addAttribute("medications", medicationRepository.findAll());
        return "medication-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("medication", new Medication());
        return "medication-form";
    }

    @PostMapping("/save")
    public String saveMedication(@ModelAttribute Medication medication) {
        medicationRepository.save(medication);
        return "redirect:/admin/medications";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Medication medication = medicationRepository.findById(id).orElseThrow();
        model.addAttribute("medication", medication);
        return "medication-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedication(@PathVariable("id") Long id) {
        medicationRepository.deleteById(id);
        return "redirect:/admin/medications";
    }
}
