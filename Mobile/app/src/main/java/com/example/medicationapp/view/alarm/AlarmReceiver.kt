package com.example.medicationapp.view.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Client

import com.example.medicationapp.view.popup.AlarmDialogActivity
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.model.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReceiver() : BroadcastReceiver() {

    private lateinit var clientRepository: ClientRepository
    var ringtone: Ringtone? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent?) {
        // Play alarm sound
        AlarmConfig.play(context)

        // Retrieve medication from intent
        Log.d("AlarmReceiver", "Client Medication: 1")
        val test = intent?.getStringExtra("test")
        Log.d("AlarmReceiver", "Test extra: $test")

        val alarmIntent = Intent(context, AlarmDialogActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("test", "hello")
        }

        context.startActivity(alarmIntent)

    }


    fun stop() {
            ringtone?.stop()
        }
    }

