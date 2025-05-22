//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.AdherenceLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdherenceLogRepository extends JpaRepository<AdherenceLog, Long> {

    AdherenceLog findByClientMedicationClientMedicationId(Long id);

    AdherenceLog findByClientMedication_ClientMedicationId(Long id);


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