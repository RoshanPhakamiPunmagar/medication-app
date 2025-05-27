// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.AdherenceLog;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for accessing and modifying AdherenceLog records in the database.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface AdherenceLogRepository extends JpaRepository<AdherenceLog, Long> {

    /**
     * Finds a single AdherenceLog by the associated ClientMedication ID.
     *
     * @param id the ID of the associated ClientMedication.
     * @return the AdherenceLog associated with the given ClientMedication ID.
     */
    AdherenceLog findByClientMedicationClientMedicationId(Long id);

    /**
     * Finds an AdherenceLog by the ClientMedication ID using property path syntax.
     *
     * @param id the ClientMedication ID.
     * @return an Optional containing the AdherenceLog if found.
     */
    Optional<AdherenceLog> findByClientMedication_ClientMedicationId(Long id);

    /**
     * Custom native query to update the adherence rate for a given ClientMedication.
     *
     * This method performs a direct SQL update on the 'adherence_log' table. The use of
     * @Modifying and @Transactional ensures the query is executed as a data modification
     * inside a transactional context.
     *
     * @param adherenceRate the new adherence rate to set.
     * @param clientMedicationId the ID of the ClientMedication whose adherence log should be updated.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE adherence_log SET adherence_rate = :adherenceRate " +
            "WHERE client_medication_client_medication_id = :clientMedicationId",
            nativeQuery = true)
    void updateAdherenceLogByAdherenceRate(
            @Param("adherenceRate") Double adherenceRate,
            @Param("clientMedicationId") Long clientMedicationId
    );
}
