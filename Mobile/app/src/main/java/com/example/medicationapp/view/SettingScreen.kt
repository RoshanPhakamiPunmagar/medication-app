package com.example.medicationapp.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(context:Context, navController: NavHostController) {


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
                    sharedPref.edit().remove("user_role").apply()



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
