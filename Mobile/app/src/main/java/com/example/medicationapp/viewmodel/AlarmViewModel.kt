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
/**
 * ViewModel class for managing alarm-related data and logic.
 *
 * Extends AndroidViewModel to have access to the Application context,
 * which can be used for operations requiring context within the ViewModel.
 *
 * @param application The Application instance used to retrieve the application context.
 */


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