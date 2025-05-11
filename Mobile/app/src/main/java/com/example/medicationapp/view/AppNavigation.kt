// Required imports for building composable navigation and UI
package com.example.medicationapp.view

// Android and Jetpack Compose dependencies
import android.content.Context
import android.os.Build
import android.util.Log
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

// Database and repositories
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.repository.MedicationRepository

// Screens
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import com.example.medicationapp.view.managerviews.ReportScreen

// Icons for bottom navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.view.alarm.AlarmScheduler
import com.example.medicationapp.viewmodel.AlarmViewModel
import com.example.medicationapp.viewmodel.ApiService

// --- Define bottom navigation items for Manager ---
sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object AssignCarer : BottomNavItemForManager("assign_carer", Icons.Default.Home, "Assign Carer")
    object AssignMedication : BottomNavItemForManager("assign_medication", Icons.Default.Add, "Reminders")
    object Reports : BottomNavItemForManager("generate_reports", Icons.Default.DateRange, "Reports")
    object Settings : BottomNavItemForManager("settings", Icons.Default.Settings, "Settings")
}

// --- Define bottom navigation items for Carer ---
sealed class BottomNavItemForCarer(val route: String, val icon: ImageVector, val label: String) {
    object SeeClient : BottomNavItemForCarer("client_selection/{carerId}", Icons.Default.Home, "See Client")
    object IncidentReport : BottomNavItemForCarer("assign_medication", Icons.Default.Add, "Incident Reports")
    object Settings : BottomNavItemForCarer("settings", Icons.Default.Settings, "Settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current


    val viewModel: AlarmViewModel = viewModel()

    // Get a reference to the singleton Room database
    val db = remember { AppDatabase.getDatabase(context) }

    // Create and remember an instance of the ClientRepository
    val clientRepository = remember {
        ClientRepository(
            clientDao = db.clientDao(),
            clientMedicationDao = db.clientMedicationDao(),
            medicationDao = db.medicationDao(),
            medicationLogDao = db.medicationLogDao(),
            adherenceLogDao = db.adherenceLogDao(),
            userDao = db.userDao()
        )
    }

    // Create and remember an instance of the MedicationRepository
    val medicationRepository = remember {
        MedicationRepository(
            medicationDao = db.medicationDao(),
            medicationLogDao = db.medicationLogDao(),
            clientMedicationDao = db.clientMedicationDao(),
        )
    }

    // Define the navigation graph with start destination as login screen
    NavHost(navController = navController, startDestination = "login") {

        // Login screen route
        composable("login") {
            LoginScreen(
                context = context,
                onLoginSuccess = { role, userId ->
                    // Store user session data in SharedPreferences
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_role", role)
                        putLong("user_id", userId)
                        apply()
                        }
                    val storedUserId = sharedPref.getLong("user_id", -1L)
                    Log.d("Sign in", "Stored user ID: $storedUserId")

                    viewModel.scheduleAlarmsForCarer(userId = storedUserId)
                    // Navigate based on user role
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

        // Signup screen route
        composable("signup") {
            SignupScreen(
                context = context,
                onSignupSuccess = {
                    navController.popBackStack() // Return to previous screen after signup
                }
            )
        }

        // Manager dashboard route
        composable("manager_dashboard") {
            ManagerMainScreen(
                clientRepository = clientRepository,
                medicationRepository = medicationRepository,
                context = context,
                navController = navController
            )
        }

        // Carer dashboard route with carerId as argument
        composable(
            "carer_dashboard/{carerId}",
            arguments = listOf(navArgument("carerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            CarrerMainScreen(
                clientRepository = clientRepository,
                navController = navController,
                carerId = carerId,
                context = context,
            )
        }

        // Route to view and manage list of clients (for managers)
        composable("manage_clients") {
            ClientListScreen(clientRepository = clientRepository)
        }

        // Route for assigning medication to clients
        composable("assign_medication") {
            // Call AssignMedicationScreen with appropriate parameters
            AssignMedicationScreen(
                viewModel = viewModel(),
                clientViewModel = viewModel() // You can use other ViewModels or pass them if needed
            )
        }


        // Route for assigning carers to clients
        composable("assign_carer") {
            AssignCarerScreen()
        }

        // Route for generating reports
        composable("reports") {
            ReportScreen()
        }

        // Route for carer to select a client (carerId is passed as an argument)
        composable(
            "client_selection/{carerId}",
            arguments = listOf(navArgument("carerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            ClientSelectionScreen(
                clientRepository = clientRepository,
                navController = navController,
                carerId = carerId
            )
        }

        // Route to write incident notes
        composable("incident_notes") {
            IncidentNotesScreen()
        }

        // Route for app settings
        composable("settings") {
            SettingScreen(context = context, navController = navController)
        }
    }
}
