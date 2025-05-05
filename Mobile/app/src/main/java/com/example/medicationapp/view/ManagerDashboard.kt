package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.navbar.ContentScreen
import com.example.medicationapp.navbar.NavItem
import com.example.medicationapp.repository.MedicationRepository
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ReportScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerMainScreen(
    clientRepository: ClientRepository,
    medicationRepository: MedicationRepository,
    context: Context,
    navController: NavHostController
) {
    val bottomNavController = rememberNavController()


    val bottomNavItems = listOf(
        BottomNavItemForManager.AssignCarer,
        BottomNavItemForManager.AssignMedication,
        BottomNavItemForManager.Reports,
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
                startDestination = BottomNavItemForManager.AssignCarer.route
            ) {
                composable(BottomNavItemForManager.AssignCarer.route) {
                    AssignCarerScreen(clientRepository = clientRepository)
                }
                composable(BottomNavItemForManager.AssignMedication.route) {
                    AssignMedicationScreen(
                        clientRepository = clientRepository,
                        medicationRepository = medicationRepository
                    )
                }
                composable(BottomNavItemForManager.Reports.route) {
                    ReportScreen()
                }
                composable(BottomNavItemForManager.Settings.route) {
                    SettingScreen(context, navController)
                }
            }
        }
    }
}
