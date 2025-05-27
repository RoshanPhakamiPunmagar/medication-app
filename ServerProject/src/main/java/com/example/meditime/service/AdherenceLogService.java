// Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.AdherenceLog;
import com.example.meditime.repository.AdherenceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing AdherenceLog entities.
 * Acts as an intermediary between controllers and the repository,
 * encapsulating business logic related to medication adherence logging.
 */
@Service
public class AdherenceLogService {

    @Autowired
    private AdherenceLogRepository adherenceLogRepository;

    /**
     * Retrieves all adherence logs from the database.
     *
     * @return List of all AdherenceLog records.
     */
    public List<AdherenceLog> getAllLogs() {
        return adherenceLogRepository.findAll();
    }

    /**
     * Saves a new adherence log or updates an existing one.
     *
     * @param log The AdherenceLog entity to be saved.
     */
    public void saveLog(AdherenceLog log) {
        adherenceLogRepository.save(log);
    }

    /**
     * Alias for saveLog; logs medication adherence for a client.
     *
     * @param log The AdherenceLog entity to be saved.
     */
    public void logAdherence(AdherenceLog log) {
        adherenceLogRepository.save(log);
    }

    /**
     * Retrieves the adherence log associated with a specific client medication ID.
     *
     * @param clientMedicationId The ID of the client medication.
     * @return The corresponding AdherenceLog entity, or null if none found.
     */
    public AdherenceLog getLogsByClientMedicationId(Long clientMedicationId) {
        return adherenceLogRepository.findByClientMedicationClientMedicationId(clientMedicationId);
    }

    /**
     * Updates the adherence rate for a given client medication.
     *
     * @param adherenceRate The new adherence rate value.
     * @param clientMedicationId The ID of the client medication whose adherence rate is to be updated.
     */
    public void updateAdheranceRate(Double adherenceRate, Long clientMedicationId) {
        adherenceLogRepository.updateAdherenceLogByAdherenceRate(adherenceRate, clientMedicationId);
    }
}
