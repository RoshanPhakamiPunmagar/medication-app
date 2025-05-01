package com.example.medicationapp.navbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    //Bottom bar
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
                NavItem("Add", Icons.Default.Add),
        NavItem("Report", Icons.Default.DateRange),
                NavItem("Settings", Icons.Default.Settings),
    )

    var selectedState by remember { mutableIntStateOf(0) }

    Scaffold (modifier = Modifier.fillMaxSize(),
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
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedState == index,
                        onClick = {selectedState = index},
                        icon = { Icon(imageVector = navItem.icon, contentDescription = "Icon") },
                        label = {
                            Text(
                                text = navItem.label
                            )
                        }
                    )
                }
            }
        }
    ) {
            innerPadding -> ContentScreen(Modifier.padding(innerPadding), selectedState)

    }
}



@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int){
//    when(selectedIndex) {
//        0 ->  LabelsAndButtons(modifier)
//        1 -> LabelsAndButtonsForTwoMeds(modifier)
   // }
}