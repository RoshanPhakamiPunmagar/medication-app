package com.example.medicationapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationapp.model.MedicationInteraction

@Dao
interface MedicationInteractionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: MedicationInteraction)

    @Query("SELECT * FROM medication_interactions WHERE medication_id_1 = :id OR medication_id_2 = :id")
    suspend fun getInteractionsForMedication(id: Int): List<MedicationInteraction>
}
