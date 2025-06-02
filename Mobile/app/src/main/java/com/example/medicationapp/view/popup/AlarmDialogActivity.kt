package com.example.medicationapp.view.popup

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.dto.ClientMedicationDTO

/**
 * Activity to display an alarm dialog screen for medication reminders.
 *
 * AlarmDialogActivity:
 * - Extends ComponentActivity to host a Compose UI.
 * - Uses @RequiresApi to ensure compatibility with Android TIRAMISU or higher.
 * - Logs receipt of the medication intent for debugging.
 * - Sets the content to AlarmDialogScreen composable.
 * - Finishes the activity when the dialog is dismissed.
 */


class AlarmDialogActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the clientMedication object in your UI logic
        val clientMedication = intent?.getSerializableExtra("client_medication") as? ClientMedicationDTO
        setContent {
            AlarmDialogScreen(clientMedication = clientMedication, onDismiss = {  finish()

            })
        }
    }
}