package com.example.medicationapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.view.alarm.AlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext

    val scheduler = AlarmScheduler(context)


    fun scheduleAlarmsForCarer(clientMedications: List<ClientMedicationDTO>) {
        Log.d("size",clientMedications.size.toString())
        viewModelScope.launch(Dispatchers.IO) {

            for (client in clientMedications) {
                Log.d("size",client.scheduledTimes.toString())
                client.let(scheduler::setUpAlarmDateRange)
            }
        }
    }
}