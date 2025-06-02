package com.example.medicationapp.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.viewmodel.ClientMedicationViewModel
import kotlinx.coroutines.NonCancellable.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(context:Context, navController: NavHostController) {
    val clientMedicationViewModel : ClientMedicationViewModel = viewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    val storedUserId = sharedPref.getLong("user_id", -1L)
                    sharedPref.edit().remove("user_role").apply()

                    clientMedicationViewModel.stopAllPeriodicFetches()

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true } // clears backstack
                    }
                }
            ) {
                Text("Logout")
            }
        }
    }
}


