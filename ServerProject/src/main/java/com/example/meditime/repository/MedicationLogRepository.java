// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.MedicationLog;
import com.example.meditime.model.MedicationLog.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for MedicationLog entity.
 *
 * Provides methods to perform CRUD operations and custom queries
 * related to medication adherence logs for clients and medications.
 */
@Repository
public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {

    /**
     * Retrieves all medication logs associated with a specific client.
     *
     * @param clientId the ID of the client
     * @return a list of MedicationLog entries for the given client
     */
    List<MedicationLog> findByClientMedication_Client_ClientId(Long clientId);

    /**
     * Counts the number of medication logs for a client with a specific status.
     * Useful for calculating adherence rates (e.g., how many times medication was taken or missed).
     *
     * @param clientId the ID of the client
     * @param status the status of the medication log (e.g., TAKEN, MISSED)
     * @return the count of medication logs matching the criteria
     */
    long countByClientMedication_Client_ClientIdAndStatus(Long clientId, Status status);

    /**
     * Retrieves medication logs for a client filtered by medication name.
     * This can be used to get logs for a specific medication of the client.
     *
     * @param clientId the ID of the client
     * @param medicationName the name of the medication
     * @return a list of MedicationLog entries matching the client and medication name
     */
    List<MedicationLog> findByClientMedication_Client_ClientIdAndClientMedication_Medication_Name(Long clientId, String medicationName);

    /**
     * Retrieves all medication logs for a specific client medication assignment.
     *
     * @param clientMedicationId the ID of the client medication
     * @return a list of MedicationLog entries for the specified client medication
     */
    List<MedicationLog> findByClientMedication_ClientMedicationId(Long clientMedicationId);
}
