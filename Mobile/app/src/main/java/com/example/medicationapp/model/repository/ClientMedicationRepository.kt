package com.example.medicationapp.model.repository

import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.dao.ClientMedicationDao

class ClientMedicationRepository(private val dao: ClientMedicationDao) {

    suspend fun assignMedication(clientMedication: ClientMedication): Long {
        return dao.insertClientMedication(clientMedication)
    }

    suspend fun getAllAssignments(): List<ClientMedication> {
        return dao.getAllClientMedications()
    }

    suspend fun getClientsForMedication(medicationId: Int): List<ClientMedication> {
        return dao.getClientsForMedication(medicationId)
    }

    suspend fun updateAssignment(clientMedication: ClientMedication) {
        dao.updateClientMedication(clientMedication)
    }

    suspend fun deleteAssignment(clientMedication: ClientMedication) {
        dao.deleteClientMedication(clientMedication)
    }
}
