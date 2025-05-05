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


    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent?) {

         val db = AppDatabase.getDatabase(context)
        clientRepository = ClientRepository(db.clientDao())


        val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userRole = sharedPref.getString("user_role", null)
        val userId = sharedPref.getLong("user_id", 0)

        Log.d("Client Check", "$userId")
        GlobalScope.launch(Dispatchers.IO) {
            val clientList = clientRepository.getClientByCarerId(userId)

            for(client in clientList){

                Log.d("Client assigneed", "${client.carerId}")
            }
            val isCarerClient = clientList.any { it.carerId == userId
            }

            Log.d("Client", "$isCarerClient")
            Log.d("Client2", "$userId")


            if (userRole != "Carer" || !isCarerClient) {
                Log.d("AlarmReceiver", "Alarm ignored: not a Carer or not a client match.")
                return@launch
            }

            Log.d("AlarmReceiver", "Alarm triggered!")
            AlarmConfig.play(context)

            // Retrieve medication from intent
            val clientMedication: ClientMedication? = intent?.getParcelableExtra("medication", ClientMedication::class.java)

            clientMedication?.let {
                Log.d("AlarmReceiver", "Client Medication: ${it.medicationId}")

                val alarmIntent = Intent(context, AlarmDialogActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("medication", it)
                }

                context.startActivity(alarmIntent)
            }
        }
    }


    fun stop() {
            ringtone?.stop()
        }
    }

