package com.example.medicationapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.controller.MedicationController
import com.example.medicationapp.controller.ReminderController
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carer.OfflineModeScreen
import com.example.medicationapp.view.carer.RemindersScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import com.example.medicationapp.view.managerviews.LiveComplianceScreen
import com.example.medicationapp.view.managerviews.ReportsScreen
import com.example.medicationapp.view.managerviews.ScanMedicationScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context       = LocalContext.current

    val clientController     = remember { ClientController(context) }
    val medicationController = remember { MedicationController(context) }
    val reminderController = remember { ReminderController(context) }

    NavHost(navController = navController, startDestination = "login") {

        // Login Screen
        composable("login") {
            LoginScreen(
                context = context,
                onLoginSuccess = { role ->
                    when (role) {
                        "Manager" -> navController.navigate("manager_dashboard")
                        "Carer"   -> navController.navigate("carer_dashboard")  // Default clientId
                    }
                },
                onNavigateToSignup = {
                    navController.navigate("signup")
                }
            )
        }

        // Signup Screen
        composable("signup") {
            SignupScreen(
                context = context,
                onSignupSuccess = {
                    navController.popBackStack()  // back to login
                }
            )
        }

        // Manager Dashboard
        composable("manager_dashboard") {
            ManagerDashboard(
                onNavigateToClients = { navController.navigate("manage_clients") },
                onNavigateToAssignMedication = { navController.navigate("assign_medication") },
                onNavigateToAssignCarer = { navController.navigate("assign_carer") },
                onNavigateToScanMedication = { navController.navigate("scan_medication") },
                onNavigateToReports = { navController.navigate("generate_reports") },
                onNavigateToLiveCompliance = { navController.navigate("live_compliance") },
            )
        }

        // Carer Dashboard
        composable("carer_dashboard") {

            CarerDashboard(
                onNavigateToReminders = { navController.navigate("reminders") },
                onNavigateToClientSelection = { navController.navigate("client_selection") },
                onNavigateToIncidentNotes = { navController.navigate("incident_notes") },
                onNavigateToOfflineMode = { navController.navigate("offline_mode") }
            )
        }


        // Manager sub‐screens
        composable("manage_clients") {
            ClientListScreen(clientController = clientController)
        }

        composable("assign_medication") {
            AssignMedicationScreen(
                clientController = clientController,
                medicationController = medicationController
            )
        }

        composable("assign_carer") {
            AssignCarerScreen()
        }

        composable("scan_medication") {
            ScanMedicationScreen()
        }

        composable("generate_reports") {
            ReportsScreen()
        }

        composable("live_compliance") {
            LiveComplianceScreen()
        }

        // Optional clientId in path
        composable("client_selection") {
            ClientSelectionScreen(
                clientId = null,
                clientController = clientController,
                navController = navController
            )
        }

        composable("client_selection/{clientId}") { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId")?.toLongOrNull()
            ClientSelectionScreen(
                clientId = clientId,
                clientController = clientController,
                navController = navController
            )
        }


        composable("reminders") {
            RemindersScreen(reminderController = reminderController)
        }


        composable("incident_notes") {
            IncidentNotesScreen()
        }

        composable("offline_mode") {
            OfflineModeScreen()
        }
    }
}


