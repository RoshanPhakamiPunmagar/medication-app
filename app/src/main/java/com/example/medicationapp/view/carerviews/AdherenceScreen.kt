package com.example.medicationapp.view.carerviews

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.model.AdherenceLog

@Composable
fun AdherenceScreen(
    clientMedicationId: Long,
    userId: Long,
    onAdherenceLogged: () -> Unit,
    controller: ClientController
) {
    var adherenceRate by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Log Adherence", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = adherenceRate,
            onValueChange = { adherenceRate = it },
            label = { Text("Adherence Rate (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                val rate = adherenceRate.toDoubleOrNull()
                if (rate != null && rate in 0.0..100.0) {
                    isSaving = true
                    coroutineScope.launch {
                        controller.insertAdherenceLog(
                            AdherenceLog(
                                clientMedicationId = clientMedicationId,
                                userId = userId,
                                checkedTime = LocalDateTime.now(),
                                adherenceRate = rate
                            )
                        )
                        Toast.makeText(context, "Adherence logged", Toast.LENGTH_SHORT).show()
                        onAdherenceLogged()
                    }
                } else {
                    Toast.makeText(context, "Please enter a valid % (0-100)", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !isSaving
        ) {
            Text(if (isSaving) "Saving..." else "Submit")
        }
    }
}
