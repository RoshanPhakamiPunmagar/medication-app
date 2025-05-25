// Required imports for building composable navigation and UI
package com.example.medicationapp.view

// Android and Jetpack Compose dependencies
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


// Screens
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import com.example.medicationapp.view.managerviews.ReportScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.util.TokenManager
import com.example.medicationapp.viewmodel.AlarmViewModel
import com.example.medicationapp.viewmodel.ClientMedicationViewModel
import com.example.medicationapp.viewmodel.ClientViewModel
import com.example.medicationapp.viewmodel.UserViewModel


sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object ManageClients : BottomNavItemForManager("manage_clients", Icons.Default.Home, "Clients")
    object AssignCarer : BottomNavItemForManager("assign_carer", Icons.Default.Add, "Assign Carer")
    object AssignMedication : BottomNavItemForManager("assign_medication", Icons.Default.AlarmAdd, "Reminders")
//    object Reports : BottomNavItemForManager("generate_reports", Icons.Default.DateRange, "Reports")
    object Settings : BottomNavItemForManager("settings", Icons.Default.Settings, "Settings")
}

sealed class BottomNavItemForCarer(val route: String, val icon: ImageVector, val label: String) {
    object SeeClient : BottomNavItemForCarer("client_selection", Icons.Default.Home, "See Client")
//    object IncidentReport : BottomNavItemForCarer("assign_medication", Icons.Default.Add, "Incident Reports")
    object Settings : BottomNavItemForCarer("settings", Icons.Default.Settings, "Settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current

    val viewModelAlarm: AlarmViewModel = viewModel()

    val viewModelClientMeds: ClientMedicationViewModel = viewModel()
    val clientMedsData by viewModelClientMeds.clientsMedsLoggedUser.collectAsState()
    val storedUserId = rememberSaveable { mutableStateOf(-1L) }

    LaunchedEffect(storedUserId.value) {
        if (storedUserId.value != -1L) {
            viewModelClientMeds.startFetchingMedsPeriodically(storedUserId.value)
        }
    }
    LaunchedEffect(clientMedsData) {
        clientMedsData?.let { viewModelAlarm.scheduleAlarmsForCarer(it) }
    }

    // Define the navigation graph with start destination as login screen
    NavHost(navController = navController, startDestination = "login") {


        composable("login") {
            val context = LocalContext.current
            val userViewModel: UserViewModel = viewModel()

            LoginScreen(
                onLoginSuccess = { role, userId ->
                    // Save user session info
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_role", role)
                        putLong("user_id", userId)
                        apply()
                    }

                    // Log user ID for debug
                    storedUserId.value = userId

                    //viewModelClientMeds.fetchClientMedsOfLoggedUser(storedUserId.value)
                    // Navigate based on role
                    when (role) {
                        "Manager" -> navController.navigate("manager_dashboard")
                        "Carer" -> navController.navigate("carer_dashboard")
                        else -> Log.w("Login", "Unknown role: $role")
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
                onSignupSuccessNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }



        // Manager dashboard route
        composable("manager_dashboard") {
            ManagerMainScreen(
                context = context,
                navController = navController
            )
        }

        // Carer dashboard route with carerId as argument
        composable(
            "carer_dashboard"
        ) {
            val carerId = storedUserId
            CarrerMainScreen(
                navController = navController,
                carerId = carerId.value,
                context = context
            )
        }

        // Route to view and manage list of clients (for managers)
        composable("manage_clients") {
            val clientViewModel: ClientViewModel = viewModel()
            ClientListScreen(clientViewModel = clientViewModel)
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
            "client_selection") {
            ClientSelectionScreen(
                clientMedicationViewModel = viewModel(),
                clientMedsDetailsViewModel = viewModel(),
                carerId = storedUserId.value
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
