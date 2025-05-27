// Amy Wickham 121785021
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing MedicationLog entities.
 * Handles CRUD operations and business logic around medication logs,
 * including adherence calculation and logging medication statuses.
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

    /**
     * Constructor to inject dependencies related to adherence logs.
     */
    public MedicationLogService(AdherenceLogService adherenceLogService, AdherenceLogRepository adherenceLogRepository) {
        this.adherenceLogService = adherenceLogService;
        this.adherenceLogRepository = adherenceLogRepository;
    }

    /**
     * Fetch all medication logs.
     * @return list of all MedicationLog entities.
     */
    public List<MedicationLog> findAll() {
        return medicationLogRepository.findAll();
    }

    /**
     * Save a medication log based on the provided DTO.
     * Validates input, converts DTO to entity, manages related entities,
     * and updates adherence logs accordingly.
     *
     * @param logDto the DTO containing medication log data
     * @throws IllegalArgumentException if the DTO is null or time format is invalid
     * @throws EntityNotFoundException if related ClientMedication or Carer is not found
     */
    @Transactional
    public void save(MedicationLogDTO logDto) {
        if (logDto == null) {
            throw new IllegalArgumentException("MedicationLogDTO cannot be null");
        }

        // Fetch related entities to ensure they exist before proceeding
        ClientMedication clientMedication = clientMedicationRepository
                .findById(logDto.getClientMedicationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ClientMedication not found with id: " + logDto.getClientMedicationId()));

        User carer = userRepository.findById(logDto.getCarerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carer not found with id: " + logDto.getCarerId()));

        // Map DTO to entity and handle time parsing
        MedicationLog medicationLog = new MedicationLog();
        medicationLog.setStatus(MedicationLog.Status.valueOf(logDto.getStatus().toString()));
        medicationLog.setNotes(logDto.getNotes());

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

        // Save the medication log to the database
        MedicationLog savedLog = medicationLogRepository.save(medicationLog);

        // Update adherence log for this client medication
        updateAdherenceLog(clientMedication.getClientMedicationId(), savedLog);
    }

    /**
     * Updates or creates an adherence log based on medication logs of a client medication.
     * Calculates adherence rate from logs and saves it.
     *
     * @param clientMedicationId the ID of the client medication
     * @param savedMedicationLog the newly saved medication log
     */
    private void updateAdherenceLog(Long clientMedicationId, MedicationLog savedMedicationLog) {
        // Retrieve all medication logs for the given client medication
        List<MedicationLog> logs = medicationLogRepository
                .findByClientMedication_ClientMedicationId(clientMedicationId);

        if (logs.isEmpty()) {
            return; // no logs to process
        }

        // Calculate adherence rate based on log statuses
        double adherenceRate = logs.stream()
                .mapToDouble(log -> switch (log.getStatus()) {
                    case Given -> 100.0;
                    case Late -> 75.0;
                    case Skipped, Missed -> 0.0;
                })
                .average()
                .orElse(0.0);

        // Find existing adherence log or create a new one
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

        // Update adherence rate and save the adherence log
        adherenceLog.setAdherenceRate(adherenceRate);
        adherenceLogRepository.save(adherenceLog);
    }

    /**
     * Retrieves medication logs by client medication ID.
     *
     * @param id the client medication ID
     * @return list of medication logs for the specified client medication
     */
    public List<MedicationLog> findMedicalLogByClientMedication(Long id) {
        return medicationLogRepository.findByClientMedication_ClientMedicationId(id);
    }

    /**
     * Logs medication status for a specific client and medication name.
     * Creates and saves a MedicationLog entity with the current timestamp.
     *
     * @param clientId the client ID
     * @param medicationName the name of the medication
     * @param status the medication status (e.g., Given, Skipped)
     */
    public void logMedicationStatus(Long clientId, String medicationName, String status) {
        List<ClientMedication> meds = clientMedicationRepository.findByClient_ClientId(clientId);

        Optional<ClientMedication> match = meds.stream()
                .filter(cm -> cm.getMedication().getName().equalsIgnoreCase(medicationName))
                .findFirst();

        if (match.isPresent()) {
            MedicationLog log = new MedicationLog();
            log.setClientMedication(match.get());
            log.setStatus(MedicationLog.Status.valueOf(status));  // Should match enum values
            log.setActualTime(LocalTime.now().toString());
            log.setScheduledTime(LocalTime.now().toString()); // or use actual scheduled time if available

            medicationLogRepository.save(log);
            System.out.println("Medication log recorded.");
        } else {
            System.out.println("Medication not found for client.");
        }
    }

    /**
     * Find a medication log by its ID.
     *
     * @param id the ID of the medication log
     * @return the MedicationLog if found, otherwise null
     */
    public MedicationLog findById(Long id) {
        return medicationLogRepository.findById(id).orElse(null);
    }

    /**
     * Delete a medication log by its ID.
     *
     * @param id the ID of the medication log to delete
     */
    public void delete(Long id) {
        medicationLogRepository.deleteById(id);
    }
}
