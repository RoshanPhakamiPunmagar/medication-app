package com.example.medicationapp.model.dao

import androidx.room.*
import com.example.medicationapp.model.Medication

@Dao
interface MedicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication): Long

    @Query("SELECT * FROM medications")
    suspend fun getAllMedications(): List<Medication>

    @Query("SELECT * FROM medications WHERE medicationId = :id")
    suspend fun getMedicationById(id: Long): Medication?

    @Update
    suspend fun updateMedication(medication: Medication)

    @Delete
    suspend fun deleteMedication(medication: Medication)
}
