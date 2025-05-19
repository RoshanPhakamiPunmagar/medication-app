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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.util.TokenManager
import com.example.medicationapp.viewmodel.AlarmViewModel
import com.example.medicationapp.viewmodel.ClientViewModel
import com.example.medicationapp.viewmodel.UserViewModel


sealed class BottomNavItemForManager(val route: String, val icon: ImageVector, val label: String) {
    object ManageClients : BottomNavItemForManager("manage_clients", Icons.Default.Person, "Clients")
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
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current

    val viewModel: AlarmViewModel = viewModel()

    // Define the navigation graph with start destination as login screen
    NavHost(navController = navController, startDestination = "login") {


        composable("login") {
            val context = LocalContext.current
            val tokenManager = remember { TokenManager(context) }
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

                    //Also save token from ViewModel
                    val token = userViewModel.status.value?.token
                    if (!token.isNullOrBlank()) {
                        tokenManager.saveToken(token)
                        Log.d("Login", "Token saved: $token")
                    } else {
                        Log.w("Login", "No token available in ViewModel!")
                    }

                    val storedUserId = sharedPref.getLong("user_id", -1L)
                    Log.d("Sign in", "Stored user ID: $storedUserId")

                    // Navigate based on role
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
            "carer_dashboard/{carerId}",
            arguments = listOf(navArgument("carerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            CarrerMainScreen(
                navController = navController,
                carerId = carerId,
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
            "client_selection/{carerId}",
            arguments = listOf(navArgument("carerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val carerId = backStackEntry.arguments?.getLong("carerId") ?: 0L
            ClientSelectionScreen(
                clientMedicationViewModel = viewModel(),
                clientMedsDetailsViewModel = viewModel(),
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
