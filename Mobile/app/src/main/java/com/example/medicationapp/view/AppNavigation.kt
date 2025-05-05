package com.example.medicationapp.view

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.repository.MedicationRepository
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import com.example.medicationapp.view.managerviews.ReportScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.example.medicationapp.model.repository.UserRepository
import com.example.medicationapp.repository.RoleRepository
import com.example.medicationapp.view.SignupScreen

sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object AssignCarer : BottomNavItemForManager("assign_carer", Icons.Default.Home, "Assign Carer")
    object AssignMedication : BottomNavItemForManager("assign_medication", Icons.Default.Add, "Reminders")
    object Reports : BottomNavItemForManager("generate_reports", Icons.Default.DateRange, "Reports")
    object Settings : BottomNavItemForManager("settings", Icons.Default.Settings, "Settings")
}

sealed class BottomNavItemForCarer(val route: String, val icon: ImageVector, val label: String) {
    object SeeClient : BottomNavItemForCarer("client_selection/{carerId}", Icons.Default.Home, "See Client")
    object IncidentReport : BottomNavItemForCarer("assign_medication", Icons.Default.Add, "Incident Reports")
    object Settings : BottomNavItemForCarer("settings", Icons.Default.Settings, "Settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    val clientRepository = remember {
        ClientRepository(
            clientDao = db.clientDao(),
            clientMedicationDao = db.clientMedicationDao(),
            medicationDao = db.medicationDao(),
            medicationLogDao = db.medicationLogDao(),
            adherenceLogDao = db.adherenceLogDao()
        )
    }

    val medicationRepository = remember {
        MedicationRepository(
            medicationDao = db.medicationDao(),
            medicationLogDao = db.medicationLogDao(),
            clientMedicationDao = db.clientMedicationDao()
        )
    }

    val userRepository = remember { UserRepository(db.userDao()) }
    val roleRepository = remember { RoleRepository(db.roleDao()) }

    NavHost(navController = navController, startDestination = "login") {

        // Login Screen
        composable("login") {
            LoginScreen(
                context = context,
                userRepository = userRepository,
                roleRepository = roleRepository,
                onLoginSuccess = { role: String, userId: Long ->
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_role", role)
                        putLong("user_id", userId)
                        apply()
                    }
                    when (role) {
                        "Manager" -> navController.navigate("manager_dashboard")
                        "Carer" -> navController.navigate("carer_dashboard/$userId")
                    }
                },
                onNavigateToSignup = {

                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen(
                context = context,
                userRepository = userRepository,
                roleRepository = roleRepository,
                onSignupSuccess = {
                    navController.popBackStack()
                }
            )
        }


        // Manager Dashboard
        composable("manager_dashboard") {
            ManagerMainScreen(
                clientRepository = clientRepository,
                medicationRepository = medicationRepository,
                context = context,
                navController = navController
            )
        }

            // Carer Dashboard
            composable(
                "carer_dashboard/{carerId}",
                arguments = listOf(navArgument("carerId") { type = NavType.LongType })
            ) { backStackEntry ->
                val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
                // Get the DAOs from the AppDatabase instance
                val clientMedicationDao = db.clientMedicationDao()
                val medicationDao = db.medicationDao()
                val medicationLogDao = db.medicationLogDao()
                CarrerMainScreen(
                    clientRepository = clientRepository,
                    navController = navController,
                    carerId = carerId,
                    context = context,
                    clientMedicationDao = clientMedicationDao,
                    medicationDao = medicationDao,
                    medicationLogDao = medicationLogDao

                )
            }

        composable("manage_clients") {
            ClientListScreen(clientRepository = clientRepository)
        }

        composable("assign_medication") {
            AssignMedicationScreen(
                clientRepository = clientRepository,
                medicationRepository = medicationRepository
            )
        }

        composable("assign_carer") {
            AssignCarerScreen(clientRepository = clientRepository)
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
                clientRepository = clientRepository,
                medicationRepository = medicationRepository,
                navController = navController,
                carerId = carerId
            )
        }

        composable("incident_notes") {
            IncidentNotesScreen()
        }

        composable("settings") {
            SettingScreen(context = context, navController = navController)
        }
    }
}
