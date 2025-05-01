package com.example.medicationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.ui.theme.MedicationAppTheme
import com.example.medicationapp.view.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.getDatabase(this) // triggers the onCreate callback if DB didn’t exist
        setContent { Surface (modifier = Modifier.fillMaxSize())
        setContent {   val navController = rememberNavController()
            Surface (modifier = Modifier.fillMaxSize())
        {
            AppNavigation()
            AppNavigation(navController)
        }
        }
    }

}
