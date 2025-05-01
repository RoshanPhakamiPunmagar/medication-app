package com.example.medicationapp.controller.popup

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.medicationapp.model.ClientMedication

class AlarmDialogActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the clientMedication object from the Intent
        val clientMedication: ClientMedication? = intent.getParcelableExtra("medication", ClientMedication::class.java)

        // Use the clientMedication object in your UI logic
        Log.d("AlarmDialogActivity", "Received medication: ${clientMedication?.medicationId}")

        // Optionally stop the ringtone when the dialog is displayed
        setContent {
            AlarmDialogScreen(onDismiss = { finish() })
        }
    }
}