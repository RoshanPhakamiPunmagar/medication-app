package com.example.medicationapp.model.repository

import com.example.medicationapp.model.MedicationInteraction
import com.example.medicationapp.model.dao.MedicationInteractionDao

class MedicationInteractionRepository(private val interactionDao: MedicationInteractionDao) {

    suspend fun getInteractionsForMedication(medicationId: Int): List<MedicationInteraction> {
        return interactionDao.getInteractionsForMedication(medicationId)
    }
}
