// Amy Wickham 121785021
// File: AdherenceLogController.java
// Description: This controller handles HTTP requests related to adherence logs for medications.
//              It provides endpoints to retrieve and store adherence logs associated with client medications.

package com.example.meditime.controller;

import com.example.meditime.model.AdherenceLog;
import com.example.meditime.service.AdherenceLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling API requests related to medication adherence logs.
 * Provides endpoints to log new adherence entries and to fetch existing adherence data
 * for a given client medication.
 */
@RestController
@RequestMapping("/api/adherence")
public class AdherenceLogController {

    @Autowired
    private AdherenceLogService adherenceLogService;

    /**
     * Retrieves the adherence log for a specific client medication ID.
     *
     * @param clientMedicationId the ID of the client-medication relationship
     * @return the corresponding AdherenceLog object
     */
    @GetMapping("/client/{clientMedicationId}")
    public ResponseEntity<AdherenceLog> getLogs(@PathVariable Long clientMedicationId) {
        return ResponseEntity.ok(adherenceLogService.getLogsByClientMedicationId(clientMedicationId));
    }

    /**
     * Records a new adherence log entry.
     *
     * @param log the adherence log information sent in the request body
     * @return a confirmation message upon successful logging
     */
    @PostMapping
    public ResponseEntity<?> logAdherence(@RequestBody AdherenceLog log) {
        adherenceLogService.logAdherence(log);
        return ResponseEntity.ok("Adherence logged.");
    }
}
