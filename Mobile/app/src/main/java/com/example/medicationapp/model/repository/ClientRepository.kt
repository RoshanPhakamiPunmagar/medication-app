package com.example.medicationapp.model.repository

// Import model classes and DAOs
import com.example.medicationapp.model.AdherenceLog
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.MedicationLog
import com.example.medicationapp.model.User
import com.example.medicationapp.model.dao.*
import com.example.medicationapp.model.dao.UserDao

class ClientRepository(
    private val clientDao: ClientDao,
    private val clientMedicationDao: ClientMedicationDao,
    private val medicationDao: MedicationDao,
    private val medicationLogDao: MedicationLogDao,
    private val adherenceLogDao: AdherenceLogDao,
    private val userDao: UserDao // ✅ Add this!
) {

    suspend fun getAllClients(): List<Client> = clientDao.getAllClients()

    suspend fun getClientsForCarer(carerId: Long): List<Client> = clientDao.getClientsForCarer(carerId)

    suspend fun getClientById(carerId: Long): Client = clientDao.getClientById(carerId)

    suspend fun logMedication(medicationLog: MedicationLog) {
        medicationLogDao.insertLog(medicationLog)
    }

    suspend fun getMedicationsForClient(clientId: Long): List<Pair<ClientMedication, String>> {
        val medications = clientMedicationDao.getMedicationsForClient(clientId)
        return medications.mapNotNull { cm ->
            medicationDao.getMedicationById(cm.medicationId)?.let { Pair(cm, it.name) }
        }
    }

    suspend fun getMedicationsOfClient(clientId: Long?): List<String> {
        if (clientId == null) return emptyList()
        val medications = clientMedicationDao.getMedicationsForClient(clientId)
        return medications.mapNotNull {
            medicationDao.getMedicationById(it.medicationId)?.name
        }
    }

    // ✅ NEW: Get all users with role ID 2 (carers)
    suspend fun getAllCarers(): List<User> {
        return userDao.getUsersByRole(roleId = 2)
    }

    // ✅ NEW: Update client (used to assign/remove carer)
    suspend fun updateClient(client: Client) {
        clientDao.updateClient(client)
    }
}
