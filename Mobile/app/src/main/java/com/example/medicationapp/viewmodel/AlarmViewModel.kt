package com.example.medicationapp.viewmodel

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.view.alarm.AlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext

    val scheduler = AlarmScheduler(context)

    private val db = AppDatabase.getDatabase(application)
    val clientRepo = ClientRepository(
        clientDao = db.clientDao(),
        clientMedicationDao = db.clientMedicationDao(),
        medicationDao = db.medicationDao(),
        medicationLogDao = db.medicationLogDao(),
        adherenceLogDao = db.adherenceLogDao(),
        userDao = db.userDao()
    )

    fun scheduleAlarmsForCarer(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val clients = clientRepo.getClientsForCarer(userId)
            for (client in clients) {
                val medications = clientRepo.getMedicationsForClient(client.clientId)
                medications.forEach { (clientMedication, _) ->
                    clientMedication.let(scheduler::setUpAlarmDateRange)
                }
            }
        }
    }
}