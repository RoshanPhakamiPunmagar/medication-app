package com.example.medicationapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.view.alarm.AlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext

    val scheduler = AlarmScheduler(context)


    fun scheduleAlarmsForCarer(clientMedications: List<ClientMedicationDTO>) {

        viewModelScope.launch(Dispatchers.IO) {

            for (client in clientMedications) {
                client.let(scheduler::setUpAlarmDateRange)
            }
        }
    }
}