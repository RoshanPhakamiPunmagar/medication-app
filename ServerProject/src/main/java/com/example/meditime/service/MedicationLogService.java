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

/**
 * MedicationLogService
 *
 * This service manages the creation, retrieval, updating, and deletion of MedicationLog records,
 * which track the administration status of medications for clients. It handles:
 *
 * - Saving new medication logs with validation and linked entities (ClientMedication, Carer).
 * - Calculating and updating adherence rates based on medication logs for each client medication.
 * - Fetching medication logs by client medication.
 * - Logging medication status by client ID and medication name.
 * - Basic CRUD operations on MedicationLog entities.
 *
 * The service collaborates with related repositories for MedicationLog, ClientMedication, User,
 * and AdherenceLog, and uses an AdherenceLogService to encapsulate adherence-related logic.
 *
 * Transactions are managed to ensure data consistency when saving logs and updating adherence.
 *
 * Proper exception handling ensures that invalid data or missing entities are handled gracefully.
 */
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
        // 1. Retrieve all medication logs associated with the given client medication ID
        List<MedicationLog> logs = medicationLogRepository
                .findByClientMedication_ClientMedicationId(clientMedicationId);

        // If there are no logs, exit early as there is nothing to process
        if (logs.isEmpty()) {
            return;
        }

        // 2. Calculate the adherence rate based on the status of each medication log
        // - Given = 100%
        // - Late = 75%
        // - Skipped or Missed = 0%
        double adherenceRate = logs.stream()
                .mapToDouble(log -> switch (log.getStatus()) {
                    case Given -> 100.0;
                    case Late -> 75.0;
                    case Skipped, Missed -> 0.0;
                })
                .average() // Calculate the average adherence score
                .orElse(0.0); // Default to 0.0 if there are no values to average

        // 3. Retrieve the existing adherence log or create a new one if it doesn't exist
        AdherenceLog adherenceLog = adherenceLogRepository
                .findByClientMedication_ClientMedicationId(clientMedicationId)
                .orElseGet(() -> {
                    // Create and initialize a new adherence log
                    AdherenceLog newLog = new AdherenceLog();
                    newLog.setClientMedication(savedMedicationLog.getClientMedication());
                    newLog.setUser(savedMedicationLog.getCarer());
                    newLog.setMedicationLog(savedMedicationLog);
                    newLog.setCheckedTime(LocalTime.now()); // Set the current time
                    return newLog;
                });

        // 4. Update the adherence rate and save the adherence log to the repository
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
            System.out.println("Medication log recorded.");
        } else {
            System.out.println("Medication not found for client.");
        }
    }

}