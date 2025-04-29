package com.example.medicationapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.medicationapp.view.carerviews.AdherenceScreen

sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object AssignCarer : BottomNavItemForManager("assign_carer", Icons.Default.Home, "Assign Carer")
    object AssignMedication : BottomNavItemForManager("assign_medication", Icons.Default.Add, "Reminders")
    object Reports : BottomNavItemForManager("generate_reports", Icons.Default.DateRange, "Reports")
    object Settings : BottomNavItemForManager("settings", Icons.Default.Settings, "Settings") // Create screen later
}

sealed class BottomNavItemForCarer(val route: String, val icon: ImageVector, val label: String) {
    object SeeClient : BottomNavItemForCarer("client_selection", Icons.Default.Home, "See Client")
    object IncidentReport : BottomNavItemForCarer("assign_medication", Icons.Default.Add, "Incident Reports")
    object Settings : BottomNavItemForCarer("settings", Icons.Default.Settings, "Settings") // Create screen later
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(modifier:Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val clientController = remember { ClientController(context) }
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
                        "Carer" -> navController.navigate("carer_dashboard")  // Default clientId
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
            ManagerMainScreen(clientController, medicationController)
        }

        composable("carer_dashboard") {
            CarrerMainScreen(
                clientController = clientController,
                navController = navController
            )
        }



        // Manager sub‐screens
        composable("manage_clients") {
            ClientListScreen(clientController = clientController)
        }

        composable(
            "adherence_screen/{medicationId}/{userId}",
            arguments = listOf(
                navArgument("medicationId") { type = NavType.LongType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getLong("medicationId") ?: 0L
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            AdherenceScreen(
                clientMedicationId = medicationId,
                userId = userId,
                onAdherenceLogged = { navController.popBackStack() },
                controller = clientController
            )
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
        composable("client_selection") {
            ClientSelectionScreen(
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



