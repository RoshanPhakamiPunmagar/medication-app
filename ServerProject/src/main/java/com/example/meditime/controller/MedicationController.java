// Amy Wickham 121785021
// File: MedicationController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.model.Medication;
import com.example.meditime.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling medication-related API endpoints.
 * Provides functionality to retrieve all medications and assign a new medication.
 */
@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    // Injects the MedicationService which contains the business logic
    @Autowired
    private MedicationService medicationService;

    /**
     * GET endpoint to retrieve all available medications from the system.
     *
     * @return a list of Medication objects
     */
    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationService.getAllMedications();
    }

    /**
     * POST endpoint to assign or add a new medication to the system.
     *
     * @param medication the medication object from the request body
     * @return a ResponseEntity confirming successful assignment
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignMedication(@RequestBody Medication medication) {
        medicationService.assignMedication(medication);
        return ResponseEntity.ok("Medication assigned successfully.");
    }
}
