//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.Medication;
import com.example.meditime.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * MedicationService
 *
 * This service provides basic CRUD operations for Medication entities.
 *
 * Responsibilities include:
 * - Retrieving all medications
 * - Saving (assigning) a new or existing medication
 * - Fetching a medication by its ID
 * - Deleting a medication by its ID
 *
 * It interacts directly with the MedicationRepository to perform these operations.
 */
@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Medication assignMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}