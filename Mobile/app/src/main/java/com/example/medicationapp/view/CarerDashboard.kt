package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.dao.ClientMedicationDao
import com.example.medicationapp.model.dao.MedicationDao
import com.example.medicationapp.model.dao.MedicationLogDao
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.repository.MedicationRepository
import com.example.medicationapp.view.carer.IncidentNotesScreen
import com.example.medicationapp.view.carerviews.ClientSelectionScreen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrerMainScreen(
    clientRepository: ClientRepository,
    medicationDao: MedicationDao,
    clientMedicationDao: ClientMedicationDao,
    medicationLogDao: MedicationLogDao, // Add medicationLogDao parameter
    navController: NavHostController,
    carerId: Long,
    context: Context
) {
    val bottomNavController = rememberNavController()

    // Create MedicationRepository using the provided DAOs
    val medicationRepository = remember {
        MedicationRepository(
            medicationDao = medicationDao,
            clientMedicationDao = clientMedicationDao,
            medicationLogDao = medicationLogDao,
        )
    }

    val bottomNavItems = listOf(
        BottomNavItemForCarer.SeeClient,
        BottomNavItemForCarer.IncidentReport,
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
                        clientRepository = clientRepository,
                        medicationRepository = medicationRepository,
                        navController = navController,
                        carerId = carerId
                    )
                }
                composable(BottomNavItemForCarer.IncidentReport.route) { IncidentNotesScreen() }
                composable(BottomNavItemForCarer.Settings.route) {
                    SettingScreen(context,navController)
                }
            }


    }
    }
}
