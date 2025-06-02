package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ClientListScreen
import com.example.medicationapp.view.managerviews.ReportScreen
import com.example.medicationapp.viewmodel.ClientViewModel

/**
 * Composable function for the Manager main screen with bottom navigation.
 *
 * Features:
 * - Displays a top app bar with the app title.
 * - Provides a bottom navigation bar with tabs for managing clients, assigning carers,
 *   assigning medication, generating reports, and accessing settings.
 * - Uses a nested NavHost for navigating between different manager-related screens.
 * - Maintains and restores navigation state for smooth user experience.
 *
 * @param context Context used for navigation and other operations.
 * @param navController NavHostController for managing navigation at the app level.
 */



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerMainScreen(
    context: Context,
    navController: NavHostController
) {
    val bottomNavController = rememberNavController()


    val bottomNavItems = listOf(
        BottomNavItemForManager.ManageClients,
        BottomNavItemForManager.AssignCarer,
        BottomNavItemForManager.AssignMedication,
//        BottomNavItemForManager.Reports,
        BottomNavItemForManager.Settings
    )

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("MediTime")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            NavHost(
                navController = bottomNavController,
                startDestination = BottomNavItemForManager.ManageClients.route
            )
            {
                composable(BottomNavItemForManager.ManageClients.route) {
                    val clientViewModel: ClientViewModel = viewModel()
                    ClientListScreen(clientViewModel = clientViewModel)
                }

                composable(BottomNavItemForManager.AssignCarer.route) {
                    AssignCarerScreen()
                }
                composable(BottomNavItemForManager.AssignMedication.route) {
                    AssignMedicationScreen(
                    )
                }
//                composable(BottomNavItemForManager.Reports.route) {
//                    ReportScreen()
//                }
                composable(BottomNavItemForManager.Settings.route) {
                    SettingScreen(context, navController)
                }
            }
        }
    }
}
