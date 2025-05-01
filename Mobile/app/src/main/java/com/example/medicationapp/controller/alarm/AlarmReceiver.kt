package com.example.medicationapp.controller.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import com.example.medicationapp.controller.popup.AlarmDialogActivity
import com.example.medicationapp.model.ClientMedication

class AlarmReceiver : BroadcastReceiver() {

    var ringtone: Ringtone? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent?) {
        val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userRole = sharedPref.getString("user_role", null)

        if (userRole != "Carer") {
            Log.d("AlarmReceiver", "Alarm ignored: not a Carer.")
            return
        }

        Log.d("AlarmReceiver", "Alarm triggered!")
        AlarmConfig.play(context)





        // Fetch the medication from intent (with updated method for Parcelable)
        val clientMedication: ClientMedication? = intent?.getParcelableExtra("medication", ClientMedication::class.java)

        if (clientMedication != null) {
            Log.d("AlarmReceiver", "Client Medication: ${clientMedication.medicationId}")
        }

        val alarmIntent = Intent(context, AlarmDialogActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("medication", clientMedication) // Passing medication to the activity
        }
        context.startActivity(alarmIntent)
    }


    fun stop() {
            ringtone?.stop()
        }
    }

