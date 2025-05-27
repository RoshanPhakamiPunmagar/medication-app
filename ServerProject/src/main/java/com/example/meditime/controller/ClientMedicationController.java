// Amy Wickham 121785021
// File: ClientMedicationController.java
// Description: Handles REST API endpoints for managing medications assigned to clients in the MediTime system.

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

/**
 * REST controller that provides endpoints for assigning medications to clients,
 * retrieving client schedules, medication names, adherence rates, and client-medication associations.
 */
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

    /**
     * Returns a list of clients and their associated medications for a given carer.
     *
     * @param carerId the ID of the carer
     * @return list of ClientWithMedicationsDTO containing client and medication info
     */
    @GetMapping("/clients-with-medications/{carerId}")
    public List<ClientWithMedicationsDTO> getClientsWithMedications(@PathVariable Long carerId) {
        return clientMedicationService.getClientsWithMedications(carerId);
    }

    /**
     * Assigns a medication to a client based on the provided DTO.
     *
     * @param dto object containing clientId, medicationId, dosage, schedule, etc.
     * @return HTTP 200 OK with confirmation message
     */
    @PostMapping("/assign")
    public ResponseEntity<String> assignMedication(@RequestBody ClientMedicationDTO dto) {
        clientMedicationService.assignMedication(dto);
        return ResponseEntity.ok("Medication assigned successfully.");
    }

    /**
     * Retrieves the medication schedule for a specific client.
     *
     * @param clientId the ID of the client
     * @return list of ClientWithMedicationsDTO with schedule data
     */
    @GetMapping("/schedule/{clientId}")
    public ResponseEntity<List<ClientWithMedicationsDTO>> getClientSchedule(@PathVariable Long clientId) {
        List<ClientWithMedicationsDTO> schedule = clientMedicationService.getClientsWithMedications(clientId);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Retrieves all medication assignments for all clients under a specific carer.
     *
     * @param carerId the ID of the carer
     * @return list of ClientMedicationDTO containing detailed medication assignment info
     */
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
        return dtoList;
    }

    /**
     * Retrieves a list of medication names assigned to a specific client.
     *
     * @param clientId the ID of the client
     * @return list of medication names
     */
    @GetMapping("/names/{clientId}")
    public ResponseEntity<List<String>> getMedicationNamesForClient(@PathVariable Long clientId) {
        List<String> names = clientMedicationService.getMedicationNamesForClient(clientId);
        return ResponseEntity.ok(names);
    }

    /**
     * Calculates and returns the adherence rate for a specific client.
     *
     * @param clientId the ID of the client
     * @return adherence rate as a decimal percentage (0.0 to 1.0)
     */
    @GetMapping("/adherence/{clientId}")
    public ResponseEntity<Double> getAdherenceRate(@PathVariable Long clientId) {
        double rate = clientMedicationService.calculateAdherenceRate(clientId);
        return ResponseEntity.ok(rate);
    }
}
