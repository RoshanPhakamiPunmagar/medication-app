//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.dto.MedicationLogDTO;
import com.example.meditime.model.AdherenceLog;
import com.example.meditime.model.ClientMedication;
import com.example.meditime.model.MedicationLog;
import com.example.meditime.model.User;
import com.example.meditime.repository.AdherenceLogRepository;
import com.example.meditime.repository.ClientMedicationRepository;
import com.example.meditime.repository.MedicationLogRepository;
import com.example.meditime.repository.UserRepository;
import java.time.LocalDateTime;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationLogService {


    @Autowired
    private MedicationLogRepository medicationLogRepository;
     @Autowired
    private ClientMedicationRepository clientMedicationRepository;
      @Autowired
    private UserRepository userRepository;


    private final AdherenceLogService adherenceLogService;
    private final AdherenceLogRepository adherenceLogRepository;

    public MedicationLogService(AdherenceLogService adherenceLogService, AdherenceLogRepository adherenceLogRepository) {
        this.adherenceLogService = adherenceLogService;
        this.adherenceLogRepository = adherenceLogRepository;
    }

    public List<MedicationLog> findAll() {
        return medicationLogRepository.findAll();
    }

    @Transactional
    public void save(MedicationLogDTO logDto) {
        if (logDto == null) {
            throw new IllegalArgumentException("MedicationLogDTO cannot be null");
        }

        // 1. Fetch related entities first (fail fast)
        ClientMedication clientMedication = clientMedicationRepository
                .findById(logDto.getClientMedicationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ClientMedication not found with id: " + logDto.getClientMedicationId()));

        User carer = userRepository.findById(logDto.getCarerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carer not found with id: " + logDto.getCarerId()));

        // 2. Convert DTO to entity
        MedicationLog medicationLog = new MedicationLog();
        medicationLog.setStatus(MedicationLog.Status.valueOf(logDto.getStatus().toString()));
        medicationLog.setNotes(logDto.getNotes());

        // Handle times with proper exception handling
        try {
            if (logDto.getScheduledTime() != null) {
                medicationLog.setScheduledTime(logDto.getScheduledTime());
            }
            medicationLog.setActualTime(logDto.getActualTime() != null
                    ? logDto.getActualTime()
                    : LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:mm", e);
        }

        medicationLog.setClientMedication(clientMedication);
        medicationLog.setCarer(carer);

        // 3. Save MedicationLog
        MedicationLog savedLog = medicationLogRepository.save(medicationLog);

        // 4. Handle AdherenceLog
        updateAdherenceLog(clientMedication.getClientMedicationId(), savedLog);
    }

    private void updateAdherenceLog(Long clientMedicationId, MedicationLog savedMedicationLog) {
        // 1. Get all medication logs for this client medication
        List<MedicationLog> logs = medicationLogRepository
                .findByClientMedication_ClientMedicationId(clientMedicationId);

        if (logs.isEmpty()) {
            return;
        }

        // 2. Calculate adherence rate
        double adherenceRate = logs.stream()
                .mapToDouble(log -> switch (log.getStatus()) {
                    case Given -> 100.0;
                    case Late -> 75.0;
                    case Skipped , Missed -> 0.0;
                })
                .average()
                .orElse(0.0);

        // 3. Find existing adherence log or create new one
        AdherenceLog adherenceLog = adherenceLogRepository
                .findByClientMedication_ClientMedicationId(clientMedicationId)
                .orElseGet(() -> {
                    AdherenceLog newLog = new AdherenceLog();
                    newLog.setClientMedication(savedMedicationLog.getClientMedication());
                    newLog.setUser(savedMedicationLog.getCarer());
                    newLog.setMedicationLog(savedMedicationLog);
                    newLog.setCheckedTime(LocalTime.now());
                    return newLog;
                });

        // 4. Update and save
        adherenceLog.setAdherenceRate(adherenceRate);
        adherenceLogRepository.save(adherenceLog);
    }

    public List<MedicationLog> findMedicalLogByClientMedication(Long id) {
        return medicationLogRepository.findByClientMedication_ClientMedicationId(id);
    }

    public void logMedicationStatus(Long clientId, String medicationName, String status) {
        List<ClientMedication> meds = clientMedicationRepository.findByClient_ClientId(clientId);

        Optional<ClientMedication> match = meds.stream()
                .filter(cm -> cm.getMedication().getName().equalsIgnoreCase(medicationName))
                .findFirst();

        if (match.isPresent()) {
            MedicationLog log = new MedicationLog();
            log.setClientMedication(match.get());
            log.setStatus(MedicationLog.Status.valueOf(status));  // Should be one of: Given, Skipped, Missed, Late
            log.setActualTime(LocalTime.now().toString());
            log.setScheduledTime(LocalTime.now().toString()); // or the intended time if tracked

            medicationLogRepository.save(log);
            System.out.println("✅ Medication log recorded.");
        } else {
            System.out.println("❌ Medication not found for client.");
        }
    }




    public MedicationLog findById(Long id) {
        return medicationLogRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        medicationLogRepository.deleteById(id);
    }
}