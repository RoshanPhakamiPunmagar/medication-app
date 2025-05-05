package com.example.medicationapp.view

import android.content.Context
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
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.medicationapp.view.managerviews.ReportScreen

sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object AssignCarer : BottomNavItemForManager("assign_carer", Icons.Default.Home, "Assign Carer")
    object AssignMedication : BottomNavItemForManager("assign_medication", Icons.Default.Add, "Reminders")
    object Reports : BottomNavItemForManager("generate_reports", Icons.Default.DateRange, "Reports")
    object Settings : BottomNavItemForManager("settings", Icons.Default.Settings, "Settings") // Create screen later
}

sealed class BottomNavItemForCarer(val route: String, val icon: ImageVector, val label: String) {
    object SeeClient : BottomNavItemForCarer("client_selection/{carerId}", Icons.Default.Home, "See Client")
    object IncidentReport : BottomNavItemForCarer("assign_medication", Icons.Default.Add, "Incident Reports")
    object Settings : BottomNavItemForCarer("settings", Icons.Default.Settings, "Settings") // Create screen later
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, modifier:Modifier = Modifier) {

    val context = LocalContext.current

    val clientController = remember { ClientController(context) }
    val medicationController = remember { MedicationController(context) }

    NavHost(navController = navController, startDestination = "login") {

        // Login Screen
        composable("login") {
            LoginScreen(
                context = context,
                onLoginSuccess = { role, userId ->
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_role", role)
                        putLong("user_id", userId)
                        apply()
                    }

                    when (role) {
                        "Manager" -> navController.navigate("manager_dashboard")
                        "Carer"   -> navController.navigate("carer_dashboard/$userId")
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
            ManagerMainScreen(clientController, medicationController, context, navController)
        }

        composable("carer_dashboard/{carerId}", arguments = listOf(
            navArgument("carerId") { type = NavType.LongType }
        )) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            CarrerMainScreen(clientController = clientController, navController = navController, carerId = carerId, context)
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

        composable("reports") {
            ReportScreen()
        }

        composable(
            "client_selection/{carerId}",
            arguments = listOf(navArgument("carerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            ClientSelectionScreen(
                clientController = clientController,
                navController = navController,
                carerId = carerId
            )
        }


        composable("incident_notes") {
            IncidentNotesScreen()
        }

        composable("settings") {
            SettingScreen(context,navController)
        }

    }
}



