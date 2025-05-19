package com.example.medicationapp.view.popup

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

        // Use the clientMedication object in your UI logic
        Log.d("AlarmDialogActivity", "Received medication: Call")

        setContent {
            AlarmDialogScreen(onDismiss = { finish() })
        }
    }
}