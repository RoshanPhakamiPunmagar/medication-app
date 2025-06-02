package com.example.meditime.controller.admin;

import com.example.meditime.model.Medication;
import com.example.meditime.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * AdminMedicationController provides administrative functionality for managing medications.
 *
 * This controller allows administrators to view a list of medications, add new medications,
 * edit existing ones, and delete medications from the system.
 *
 * Endpoints:
 * - GET  /admin/medications           : Displays a list of all medications.
 * - GET  /admin/medications/add       : Shows the form to add a new medication.
 * - POST /admin/medications/save      : Saves a new or edited medication to the database.
 * - GET  /admin/medications/edit/{id} : Displays the form to edit an existing medication.
 * - GET  /admin/medications/delete/{id} : Deletes the specified medication.
 *
 * Dependencies:
 * - MedicationRepository: used for CRUD operations on Medication entities.
 */
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