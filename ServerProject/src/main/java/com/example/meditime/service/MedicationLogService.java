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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    public void save(MedicationLogDTO logDto) {
        if (logDto == null) {
            throw new IllegalArgumentException("MedicationLogDTO cannot be null");
        }

        // 1. Convert DTO to entity
        MedicationLog medicationLog = new MedicationLog();

        // 2. Set basic fields
        medicationLog.setStatus(MedicationLog.Status.valueOf(logDto.getStatus().toString()));
        medicationLog.setNotes(logDto.getNotes());

        // 3. Handle times (with null checks)
        if (logDto.getScheduledTime() != null) {
            medicationLog.setScheduledTime(logDto.getScheduledTime());
        }
        if (logDto.getActualTime() != null) {
            medicationLog.setActualTime(logDto.getActualTime());
        } else {
            medicationLog.setActualTime(LocalTime.now().toString());
        }

        // 4. Fetch and set related entities
        ClientMedication clientMedication = clientMedicationRepository
                .findById(logDto.getClientMedicationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ClientMedication not found with id: " + logDto.getClientMedicationId()));

        AdherenceLog adherenceLog = adherenceLogRepository.findByClientMedication_ClientMedicationId(logDto.getClientMedicationId());

        System.out.println(adherenceLog);
        List<MedicationLog> ml = medicationLogRepository.findByClientMedication_ClientMedicationId(logDto.getClientMedicationId());

        User carer = userRepository.findById(logDto.getCarerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carer not found with id: " + logDto.getCarerId()));

        medicationLog.setClientMedication(clientMedication);
        medicationLog.setCarer(carer);

        // 5. Save MedicationLog
        MedicationLog savedLog = medicationLogRepository.save(medicationLog);

        Double adhRate = 0.0;
        double totalAdhRate = 0;
        if (adherenceLog != null) {

            for(MedicationLog x: ml){
                System.out.println("Adh Statues 1 " + x.getStatus());
                double adherenceRate = switch (x.getStatus()) {
                    case Given -> 100.0;
                    case Late -> 75.0;  // More realistic than 50%
                    case Skipped , Missed -> 0.0;
                };
                totalAdhRate = totalAdhRate + adherenceRate;

                System.out.println("Adh Statues " + x.getStatus() + " Rate = " +     adherenceRate);

            }
            System.out.println("Adh Rate" + totalAdhRate);
            System.out.println("Adh Size " + ml.size());
            adhRate = totalAdhRate / ml.size();
            adherenceLog.setAdherenceRate(adhRate);

            adherenceLogService.updateAdheranceRate(adhRate, clientMedication.getClientMedicationId());
            return;
        }



        // 6. Create and save AdherenceLog
        adherenceLog = new AdherenceLog();
        adherenceLog.setMedicationLog(savedLog);
        adherenceLog.setClientMedication(clientMedication);
        adherenceLog.setUser(carer); // Assuming AdherenceLog needs the carer/user
        adherenceLog.setCheckedTime(LocalTime.now());

        // Calculate adherence rate
        double adherenceRate = switch (savedLog.getStatus()) {
            case Given -> 100.0;
            case Late -> 75.0;  // More realistic than 50%
            case Skipped , Missed -> 0.0;
        };
        adherenceLog.setAdherenceRate(adherenceRate);

        // 7. Save AdherenceLog
        adherenceLogService.saveLog(adherenceLog);
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