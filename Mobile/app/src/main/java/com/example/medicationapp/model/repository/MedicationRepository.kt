package com.example.medicationapp.repository

import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import com.example.medicationapp.model.MedicationLog
import com.example.medicationapp.model.dao.ClientMedicationDao
import com.example.medicationapp.model.dao.MedicationDao
import com.example.medicationapp.model.dao.MedicationLogDao
import com.example.medicationapp.viewmodel.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class MedicationRepository( private val medicationDao: MedicationDao, private val medicationLogDao: MedicationLogDao, private val clientMedicationDao: ClientMedicationDao) {


    suspend fun getAllMedications(): List<Medication> = medicationDao.getAllMedications()

    suspend fun getMedicationById(id: Long): Medication? = medicationDao.getMedicationById(id)

    suspend fun insertMedication(medication: Medication) {
        medicationDao.insertMedication(medication)
    }

    suspend fun logMedication(medicationLog: MedicationLog) {
        medicationLogDao.insertMedicationLog(medicationLog)
    }


    suspend fun getMedicationsForClient(clientId: Long): List<Pair<ClientMedication, String>> {
        val clientMedications = clientMedicationDao.getMedicationsForClient(clientId)
        return clientMedications.mapNotNull { clientMedication ->
            val medication = medicationDao.getMedicationById(clientMedication.medicationId)
            medication?.let { Pair(clientMedication, it.name) }
        }
    }


//
//    // Assign medication to a client with full schedule details
//    suspend fun assignMedicationToClient(
//        clientId: Long,
//        medicationId: Long,
//        dosage: String,
//        startDate: LocalDate,
//        endDate: LocalDate,
//        scheduledTimes: List<LocalTime>
//    ) : ClientMedication{
//        val clientMedication = ClientMedication(
//            clientId = clientId,
//            medicationId = medicationId,
//            dosage = dosage,
//            startDate = startDate,
//            endDate = endDate,
//            scheduledTimes = scheduledTimes
//        )
//        clientMedicationDao.insertClientMedication(clientMedication)
//        return clientMedication
//    }

}
