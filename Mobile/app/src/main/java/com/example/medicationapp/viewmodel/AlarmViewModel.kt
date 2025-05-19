package com.example.medicationapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext
}