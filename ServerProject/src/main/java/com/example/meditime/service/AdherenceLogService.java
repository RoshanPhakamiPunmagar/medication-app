//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.AdherenceLog;
import com.example.meditime.repository.AdherenceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for managing adherence logs related to client medications.
 *
 * Provides methods to retrieve all adherence logs, save new logs,
 * fetch logs by client medication ID, and update adherence rates.
 *
 * Interacts with the AdherenceLogRepository to perform database operations.
 */
@Service
public class AdherenceLogService {

    @Autowired
    private AdherenceLogRepository adherenceLogRepository;
    public List<AdherenceLog> getAllLogs() {
        return adherenceLogRepository.findAll();
    }

    public void saveLog(AdherenceLog log) {
        adherenceLogRepository.save(log);
    }


    public void logAdherence(AdherenceLog log) {
        adherenceLogRepository.save(log);
    }


    public AdherenceLog getLogsByClientMedicationId(Long clientMedicationId) {
        return adherenceLogRepository.findByClientMedicationClientMedicationId(clientMedicationId);
    }


    public void updateAdheranceRate(Double adherenceRate, Long clientMedicationId) {
        adherenceLogRepository.updateAdherenceLogByAdherenceRate(adherenceRate, clientMedicationId);
    }
    
}