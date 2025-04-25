package com.example.medicationapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.ui.theme.MedicationAppTheme
import com.example.medicationapp.view.AppNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.getDatabase(this)
        setContent { AppNavigation() }
    }

}
