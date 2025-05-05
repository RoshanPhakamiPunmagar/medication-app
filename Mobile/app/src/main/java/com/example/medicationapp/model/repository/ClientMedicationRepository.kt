package com.example.medicationapp.model.repository

import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.dao.ClientMedicationDao

class ClientMedicationRepository(private val clientMedicationDao: ClientMedicationDao) {

    suspend fun insertClientMedication(clientMedication: ClientMedication) {
        clientMedicationDao.insertClientMedication(clientMedication)
    }

    suspend fun updateClientMedication(clientMedication: ClientMedication) {
        clientMedicationDao.updateClientMedication(clientMedication)
    }

    suspend fun deleteClientMedication(clientMedication: ClientMedication) {
        clientMedicationDao.deleteClientMedication(clientMedication)
    }

    suspend fun getMedicationsForClient(clientId: Long): List<ClientMedication> {
        return clientMedicationDao.getMedicationsForClient(clientId)
    }
}
