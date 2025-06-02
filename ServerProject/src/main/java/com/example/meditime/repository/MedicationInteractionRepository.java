//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Medication;
import com.example.meditime.model.MedicationInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing MedicationInteraction entities.
 * Provides methods to find interactions between two medications by their IDs.
 */
public interface MedicationInteractionRepository extends JpaRepository<MedicationInteraction, Long> {

  Optional<MedicationInteraction> findByMedication1MedicationIdAndMedication2MedicationId(Medication med1, Medication med2);
Optional<MedicationInteraction> findByMedication1MedicationIdAndMedication2MedicationId(Long med1Id, Long med2Id);

}
