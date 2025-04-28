package com.example.medicationapp.view

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.controller.MedicationController
import com.example.medicationapp.navbar.ContentScreen
import com.example.medicationapp.navbar.NavItem
import com.example.medicationapp.view.managerviews.AssignCarerScreen
import com.example.medicationapp.view.managerviews.AssignMedicationScreen
import com.example.medicationapp.view.managerviews.ReportsScreen
import com.example.medicationapp.view.managerviews.ScanMedicationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerMainScreen(clientController:ClientController, medicationController:MedicationController) {
    val bottomNavController = rememberNavController() // ✅ this is okay here

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
                composable(BottomNavItemForManager.AssignCarer.route) { AssignCarerScreen() }
                composable(BottomNavItemForManager.AssignMedication.route) { AssignMedicationScreen(clientController, medicationController) }
                composable(BottomNavItemForManager.Reports.route) { ReportsScreen() }
                composable(BottomNavItemForManager.Settings.route) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Settings coming soon!")
                    }
                }
            }
        }
    }
}
//}
//@Composable
//fun ManagerDashboard(
//    onNavigateToClients: () -> Unit,
//    onNavigateToAssignMedication: () -> Unit,
//    onNavigateToAssignCarer: () -> Unit,
//    onNavigateToScanMedication: () -> Unit,
//    onNavigateToReports: () -> Unit,
//    onNavigateToLiveCompliance: () -> Unit,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Text("Manager Dashboard", style = MaterialTheme.typography.headlineMedium)
//
//        Button(onClick = onNavigateToClients, modifier = Modifier.fillMaxWidth()) {
//            Text("Manage Clients")
//        }
//
//        Button(onClick = onNavigateToAssignMedication, modifier = Modifier.fillMaxWidth()) {
//            Text("Assign Medications")
//        }
//
//        Button(onClick = onNavigateToAssignCarer, modifier = Modifier.fillMaxWidth()) {
//            Text("Assign Carers to Clients")
//        }
//
//        Button(onClick = onNavigateToScanMedication, modifier = Modifier.fillMaxWidth()) {
//            Text("Scan & Add Medication")
//        }
//
//        Button(onClick = onNavigateToReports, modifier = Modifier.fillMaxWidth()) {
//            Text("Generate Compliance Reports")
//        }
//        //Might not be needed
//        Button(onClick = onNavigateToLiveCompliance, modifier = Modifier.fillMaxWidth()) {
//            Text("View Live Compliance")
//        }
//    }
//}
