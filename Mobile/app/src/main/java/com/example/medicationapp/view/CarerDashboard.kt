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
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen

/**
 * Composable function that represents the main screen for Carer users with bottom navigation.
 *
 * Features:
 * - Displays a TopAppBar with the app name.
 * - Implements a bottom navigation bar with three items: See Client, Incident Reports, and Settings.
 * - Uses a nested NavHost to handle navigation within the Carer section.
 * - Maintains the selected bottom navigation item state across recompositions.
 * - Passes the carerId argument to relevant screens for personalized content.
 * - Integrates ViewModels scoped to the composables for managing UI data.
 *
 * @param navController The NavHostController for overall app navigation.
 * @param carerId The unique ID of the logged-in carer to fetch relevant data.
 * @param context The Android Context used for some composables like settings.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrerMainScreen(navController: NavHostController, carerId: Long,clientMedsDTO: List<ClientMedicationDTO>?, context: Context) {
    val bottomNavController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItemForCarer.SeeClient,
//        BottomNavItemForCarer.IncidentReport,
        BottomNavItemForCarer.Settings
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
                startDestination = BottomNavItemForCarer.SeeClient.route
            ) {
                composable(BottomNavItemForCarer.SeeClient.route) {
                    ClientSelectionScreen(

                        clientMedicationViewModel = viewModel(),
                        clientMedsDetailsViewModel = viewModel(),
                        clientMedsDTOs = clientMedsDTO,
                        carerId = carerId
                    )
                }
//                composable(BottomNavItemForCarer.IncidentReport.route) { IncidentNotesScreen() }
                composable(BottomNavItemForCarer.Settings.route) {
                    SettingScreen(context,navController)
                }
            }


        }
    }
}