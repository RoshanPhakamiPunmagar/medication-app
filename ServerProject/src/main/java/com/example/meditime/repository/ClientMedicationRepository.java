// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.ClientMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for performing CRUD and custom queries on the ClientMedication entity.
 */
public interface ClientMedicationRepository extends JpaRepository<ClientMedication, Long> {

    /**
     * Retrieves a list of ClientMedication records for a specific client based on their ID.
     *
     * @param clientId the ID of the client.
     * @return a list of ClientMedication entities associated with the given client.
     */
    List<ClientMedication> findByClient_ClientId(Long clientId);

    /**
     * Finds a specific ClientMedication by both client ID and medication ID.
     *
     * Useful for checking if a specific medication is prescribed to a specific client.
     *
     * @param clientId the ID of the client.
     * @param medicationId the ID of the medication.
     * @return an Optional containing the ClientMedication if found.
     */
    Optional<ClientMedication> findByClient_ClientIdAndMedication_MedicationId(Long clientId, Long medicationId);

    /**
     * Custom JPQL query to retrieve only the medication IDs for a given client.
     *
     * This improves performance when only the IDs are needed rather than full entity details.
     *
     * @param clientId the ID of the client.
     * @return a list of medication IDs associated with the specified client.
     */
    @Query("SELECT cm.medication.id FROM ClientMedication cm WHERE cm.client.clientId = :clientId")
    List<Long> findMedicationIdsByClientId(@Param("clientId") Long clientId);
}
