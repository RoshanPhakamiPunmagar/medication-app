package com.example.medicationapp.repository

import com.example.medicationapp.model.Medication
import com.example.medicationapp.model.dao.MedicationDao

class MedicationRepository(private val medicationDao: MedicationDao) {

    suspend fun addMedication(medication: Medication): Long {
        return medicationDao.insertMedication(medication)
    }

    suspend fun getAllMedications(): List<Medication> {
        return medicationDao.getAllMedications()
    }


    suspend fun updateMedication(medication: Medication) {
        medicationDao.updateMedication(medication)
    }

    suspend fun deleteMedication(medication: Medication) {
        medicationDao.deleteMedication(medication)
    }
}
