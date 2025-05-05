package com.example.medicationapp.model.repository

import com.example.medicationapp.model.AdherenceLog
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.MedicationLog
import com.example.medicationapp.model.dao.*

class ClientRepository(
    private val clientDao: ClientDao,
    private val clientMedicationDao: ClientMedicationDao,
    private val medicationDao: MedicationDao,
    private val medicationLogDao: MedicationLogDao,
    private val adherenceLogDao: AdherenceLogDao
) {

    suspend fun getAllClients(): List<Client> {
        return clientDao.getAllClients()
    }

    suspend fun getClientsForCarer(carerId: Long): List<Client> {
        return clientDao.getClientsForCarer(carerId)
    }

    suspend fun getClientById(carerId: Long): Client {
        return clientDao.getClientById(carerId)
    }

    suspend fun logMedication(medicationLog: MedicationLog) {
        medicationLogDao.insertLog(medicationLog)
    }
    suspend fun getMedicationsForClient(clientId: Long): List<Pair<ClientMedication, String>> {
        val medications = clientMedicationDao.getMedicationsForClient(clientId)

        return medications.mapNotNull { clientMedication ->
            val medication = medicationDao.getMedicationById(clientMedication.medicationId)
            medication?.let {
                Pair(clientMedication, it.name)
            }
        }
    }

    suspend fun getMedicationsOfClient(clientId: Long?): List<String> {
        if(clientId != null) {
            val medications = clientMedicationDao.getMedicationsForClient(clientId)


            return medications.mapNotNull { clientMedication ->
                val medication = medicationDao.getMedicationById(clientMedication.medicationId)
                medication?.let {
                    it.name
                }
            }
        }
        return emptyList()
    }

}
