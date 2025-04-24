package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication

class ClientController(context: Context) {
    private val clientDao = AppDatabase.getDatabase(context).clientDao()
    private val clientMedicationDao = AppDatabase.getDatabase(context).clientMedicationDao()

    suspend fun getAllClients(): List<Client> {
        return clientDao.getAllClients()
    }


    suspend fun assignMedication(clientMedication: ClientMedication) {
        clientMedicationDao.insertClientMedication(clientMedication)
    }

}
