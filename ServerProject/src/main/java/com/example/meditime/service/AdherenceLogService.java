//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.AdherenceLog;
import com.example.meditime.repository.AdherenceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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