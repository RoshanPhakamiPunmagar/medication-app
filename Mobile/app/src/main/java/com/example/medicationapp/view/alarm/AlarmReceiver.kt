package com.example.medicationapp.view.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.medicationapp.view.popup.AlarmDialogActivity


class AlarmReceiver() : BroadcastReceiver() {


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

