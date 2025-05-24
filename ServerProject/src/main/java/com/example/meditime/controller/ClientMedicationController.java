//Amy Wickham 121785021
// Amy Wickham 12178502
// File: ClientMedicationController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.dto.ClientMedicationDTO;
import com.example.meditime.dto.ClientWithMedicationsDTO;
import com.example.meditime.model.Client;
import com.example.meditime.model.ClientMedication;
import com.example.meditime.service.ClientMedicationService;
import com.example.meditime.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/medication")
public class ClientMedicationController {

    private final ClientMedicationService clientMedicationService;
    private final ClientService clientService;
    @Autowired
    public ClientMedicationController(ClientMedicationService clientMedicationService, ClientService clientService) {
        this.clientMedicationService = clientMedicationService;
        this.clientService = clientService;
    }

//    @GetMapping("/carer/{carerId}/clients-with-medications")
//    public ResponseEntity<List<ClientMedicationDTO>> getClientsWithMedications(@PathVariable Long carerId) {
//        List<ClientMedicationDTO> result = clientMedicationService.getClientsWithMedications(carerId);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/clients-with-medications/{carerId}")
    public List<ClientWithMedicationsDTO> getClientsWithMedications(@PathVariable Long carerId) {
        List<ClientWithMedicationsDTO> response = clientMedicationService.getClientsWithMedications(carerId);
        System.out.println(response.get(0).getMedications().get(0).getClientMedicationId());
//        return ResponseEntity.ok(response);
        return response;
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

    @GetMapping("get/{carerId}")
    public List<ClientMedicationDTO> getClientMedicationWithClientId(@PathVariable Long carerId) {
        List<Client> assignedClients = clientService.getAllClientsByUserId(carerId);
        List<ClientMedicationDTO> dtoList = new ArrayList<>();

        for (Client client : assignedClients) {
            List<ClientMedication> meds = clientMedicationService.getClientMedicationByClientId(client.getClientId());
            for (ClientMedication med : meds) {
                ClientMedicationDTO dto = new ClientMedicationDTO();
                dto.setClientId(med.getClient().getClientId());
                dto.setMedicationId(med.getMedication().getMedicationId());
                dto.setDosage(med.getDosage());
                dto.setStartDate(med.getStartDate());
                dto.setEndDate(med.getEndDate());
                dto.setPaused(med.isPaused());
                dto.setScheduledTimes(med.getScheduledTimes());
                dto.setMedicationName(med.getMedication().getName());
                dto.setClientName(med.getClient().getName());


                dtoList.add(dto);
            }
        }
        System.out.println(dtoList.size() + " Client Id");
        return dtoList;
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