package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.AdherenceLog
//This is AdherenceController class
class AdherenceController(context: Context) {
    private val adherenceDao = AppDatabase.getDatabase(context).adherenceLogDao()

    suspend fun logAdherence(log: AdherenceLog) {
        adherenceDao.insertAdherenceLog(log)
    }

    suspend fun viewAdherence(clientMedicationId: Int): List<AdherenceLog> {
        return adherenceDao.getLogsForClientMedication(clientMedicationId)
    }
}
