package com.example.medicationapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationapp.model.MedicationLog

@Dao
interface MedicationLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: MedicationLog)

    @Query("SELECT * FROM medication_logs WHERE client_medication_id = :cmId")
    suspend fun getLogsForClientMedication(cmId: Int): List<MedicationLog>
}
