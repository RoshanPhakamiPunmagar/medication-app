// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Medication;
import com.example.meditime.model.MedicationInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for MedicationInteraction entities.
 *
 * Provides CRUD operations and custom query methods
 * to find interactions between medications based on their IDs.
 */
public interface MedicationInteractionRepository extends JpaRepository<MedicationInteraction, Long> {

  /**
   * Finds a MedicationInteraction entity by the medication IDs of the two medications involved.
   *
   * @param med1 The first Medication object
   * @param med2 The second Medication object
   * @return An Optional containing the MedicationInteraction if found, otherwise empty
   */
  Optional<MedicationInteraction> findByMedication1MedicationIdAndMedication2MedicationId(Medication med1, Medication med2);

  /**
   * Finds a MedicationInteraction entity by the IDs of the two medications involved.
   *
   * @param med1Id The ID of the first medication
   * @param med2Id The ID of the second medication
   * @return An Optional containing the MedicationInteraction if found, otherwise empty
   */
  Optional<MedicationInteraction> findByMedication1MedicationIdAndMedication2MedicationId(Long med1Id, Long med2Id);

}
