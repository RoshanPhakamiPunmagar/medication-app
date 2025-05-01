package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import com.example.medicationapp.model.MedicationInteraction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class MedicationController(context: Context) {

    private val medicationDao = AppDatabase.getDatabase(context).medicationDao()
    private val interactionDao = AppDatabase.getDatabase(context).medicationInteractionDao()
    private val clientMedicationDao = AppDatabase.getDatabase(context).clientMedicationDao()

    suspend fun getAllMedications(): List<Medication> {
        return medicationDao.getAllMedications()
    }

    suspend fun getMedications(id:Long): Medication? {
        return medicationDao.getMedicationById(id)
    }

    suspend fun updateSchedule(
        clientMedication: ClientMedication
    ) {
        clientMedicationDao.updateClientMedication(clientMedication)
    }


    // Add new medication to the system
    suspend fun addMedication(medication: Medication) {
        medicationDao.insertMedication(medication)
    }

    // View all available medications
    suspend fun viewMedicationDetails(): List<Medication> {
        return medicationDao.getAllMedications()
    }

    // Check for interactions with a selected medication
    suspend fun checkInteractions(medicationId: Int): List<MedicationInteraction> {
        return interactionDao.getInteractionsForMedication(medicationId)
    }

    // Assign medication to a client with full schedule details
    suspend fun assignMedicationToClient(
        clientId: Long,
        medicationId: Long,
        dosage: String,
        startDate: LocalDate,
        endDate: LocalDate,
        scheduledTimes: List<LocalTime>
    ) : ClientMedication{
        val clientMedication = ClientMedication(
            clientId = clientId,
            medicationId = medicationId,
            dosage = dosage,
            startDate = startDate,
            endDate = endDate,
            scheduledTimes = scheduledTimes
        )
        clientMedicationDao.insertClientMedication(clientMedication)
        return clientMedication
    }

    // Update an existing client medication schedule
    suspend fun updateClientMedication(clientMedication: ClientMedication) {
        clientMedicationDao.updateClientMedication(clientMedication)
    }

    // Delete a medication schedule for a client
    suspend fun deleteClientMedication(clientMedication: ClientMedication) {
        clientMedicationDao.deleteClientMedication(clientMedication)
    }

    // Fetch all medications assigned to a specific client
    suspend fun getClientMedications(clientId: Long): List<ClientMedication> {
        return clientMedicationDao.getMedicationsForClient(clientId)
    }
}
