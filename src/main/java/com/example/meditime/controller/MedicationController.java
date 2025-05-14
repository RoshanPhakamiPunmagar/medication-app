
//Amy Wickham 121785021
// Amy Wickham 12178502
// File: MedicationController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.model.Medication;
import com.example.meditime.service.MedicationService;
import com.example.meditime.service.OpenFdaMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private OpenFdaMedicationService fdaService;

    @GetMapping("/import-openfda")
    public String importFromOpenFDA() {
        fdaService.fetchOpenFdaMedications();
        return "Medications imported from openFDA.";
    }


    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationService.getAllMedications();
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignMedication(@RequestBody Medication medication) {
        medicationService.assignMedication(medication);
        return ResponseEntity.ok("Medication assigned successfully.");
    }
}