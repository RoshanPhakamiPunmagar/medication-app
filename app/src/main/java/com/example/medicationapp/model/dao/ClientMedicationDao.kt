package com.example.medicationapp.model.dao

import androidx.room.*
import com.example.medicationapp.model.ClientMedication


@Dao
interface ClientMedicationDao {
    @Insert
    suspend fun insertClientMedication(medication: ClientMedication): Long

    @Update
    suspend fun updateClientMedication(medication: ClientMedication)

    @Delete
    suspend fun deleteClientMedication(medication: ClientMedication)

    @Query("SELECT * FROM client_medications WHERE clientId = :clientId")
    suspend fun getMedicationsForClient(clientId: Long): List<ClientMedication>

    @Query("SELECT * FROM client_medications WHERE medicationId = :id")
    suspend fun getClientMedicationById(id: Long): ClientMedication

    @Query("SELECT * FROM client_medications")
    suspend fun getAllClientMedications(): List<ClientMedication>

    @Query("SELECT * FROM client_medications WHERE medicationId = :medicationId")
    suspend fun getClientsForMedication(medicationId: Int): List<ClientMedication>

    @Query("SELECT * FROM client_medications WHERE clientMedicationId = :clientMedicationId")
    suspend fun getClientsMedicationById(clientMedicationId: Int): ClientMedication

}
