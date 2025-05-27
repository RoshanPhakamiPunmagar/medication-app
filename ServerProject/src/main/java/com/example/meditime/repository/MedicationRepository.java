// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Medication entity.
 *
 * Extends JpaRepository to provide standard CRUD operations.
 * Includes custom query methods to find medication by name.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    /**
     * Finds a medication by its name.
     *
     * @param name the name of the medication
     * @return an Optional containing the Medication if found, otherwise empty
     */
    Optional<Medication> findByName(String name);

}
