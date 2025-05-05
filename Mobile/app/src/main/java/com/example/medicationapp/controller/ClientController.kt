package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.AdherenceLog
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.MedicationLog

class ClientController(private val context: Context) {  // ← make 'context' a private property
    private val clientDao = AppDatabase.getDatabase(context).clientDao()
    private val clientMedicationDao = AppDatabase.getDatabase(context).clientMedicationDao()
    private val medicationLogDao = AppDatabase.getDatabase(context).medicationLogDao()
    private val medicationDao = AppDatabase.getDatabase(context).medicationDao()
    private val adherenceLogDao = AppDatabase.getDatabase(context).adherenceLogDao()

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



