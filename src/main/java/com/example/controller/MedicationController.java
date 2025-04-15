package com.example.controller;

import com.example.aimedicationweb.service.OpenFdaMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private OpenFdaMedicationService fdaService;

    @GetMapping("/import-openfda")
    public String importFromOpenFDA() {
        fdaService.fetchOpenFdaMedications();
        return "Medications imported from openFDA.";
    }

}
