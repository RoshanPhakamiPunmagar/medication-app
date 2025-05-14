//Amy Wickham 121785021
// Amy Wickham 12178502
// File: ClientMedicationController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.dto.ClientMedicationDTO;
import com.example.meditime.dto.ClientWithMedicationsDTO;
import com.example.meditime.service.ClientMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication")
public class ClientMedicationController {

    private final ClientMedicationService clientMedicationService;

    @Autowired
    public ClientMedicationController(ClientMedicationService clientMedicationService) {
        this.clientMedicationService = clientMedicationService;
    }

//    @GetMapping("/carer/{carerId}/clients-with-medications")
//    public ResponseEntity<List<ClientMedicationDTO>> getClientsWithMedications(@PathVariable Long carerId) {
//        List<ClientMedicationDTO> result = clientMedicationService.getClientsWithMedications(carerId);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/clients-with-medications/{carerId}")
    public ResponseEntity<List<ClientWithMedicationsDTO>> getClientsWithMedications(@PathVariable Long carerId) {
        List<ClientWithMedicationsDTO> response = clientMedicationService.getClientsWithMedications(carerId);
        return ResponseEntity.ok(response);
    }




    @PostMapping("/assign")
public ResponseEntity<String> assignMedication(@RequestBody ClientMedicationDTO dto) {
    clientMedicationService.assignMedication(dto);
        System.out.println("Received clientId: " + dto.getClientId());
        System.out.println("Received medicationId: " + dto.getMedicationId());
    return ResponseEntity.ok("Medication assigned successfully.");
}


    @GetMapping("/schedule/{clientId}")
    public ResponseEntity<List<ClientWithMedicationsDTO>> getClientSchedule(@PathVariable Long clientId) {
        List<ClientWithMedicationsDTO> schedule = clientMedicationService.getClientsWithMedications(clientId);
        return ResponseEntity.ok(schedule);
    }


    @GetMapping("/names/{clientId}")
    public ResponseEntity<List<String>> getMedicationNamesForClient(@PathVariable Long clientId) {
        List<String> names = clientMedicationService.getMedicationNamesForClient(clientId);
        return ResponseEntity.ok(names);
    }


    @GetMapping("/adherence/{clientId}")
    public ResponseEntity<Double> getAdherenceRate(@PathVariable Long clientId) {
        double rate = clientMedicationService.calculateAdherenceRate(clientId);
        return ResponseEntity.ok(rate);
    }
}