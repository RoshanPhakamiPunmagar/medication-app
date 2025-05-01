package com.example.medicationapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationapp.model.AdherenceLog

@Dao
interface AdherenceLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdherenceLog(log: AdherenceLog)

    @Query("SELECT * FROM adherence_logs WHERE clientMedicationId = :cmId")
    suspend fun getLogsForClientMedication(cmId: Int): List<AdherenceLog>
}
