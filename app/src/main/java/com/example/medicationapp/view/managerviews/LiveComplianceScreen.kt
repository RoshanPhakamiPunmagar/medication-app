package com.example.medicationapp.view.managerviews
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LiveComplianceScreen() {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Live Compliance", style = MaterialTheme.typography.headlineMedium)
        // TODO: Real-time medication adherence
    }
}
