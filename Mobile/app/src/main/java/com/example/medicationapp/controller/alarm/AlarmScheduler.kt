package com.example.medicationapp.controller.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.medicationapp.model.ClientMedication
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class AlarmScheduler(private val context: Context) {
    private val today = LocalDate.now()
    private val alarmManager = context.getSystemService(AlarmManager::class.java)



    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun setUpAlarm(clientMedication: ClientMedication) {
        try {
            // 1. First verify we have times
            if (clientMedication.scheduledTimes.isEmpty()) {
                Log.e("AlarmScheduler", "No scheduled times provided")
                return
            }

            //setting Client Medication
            setClientMedication(clientMedication)

            // 2. Calculate trigger time with proper debugging
            val scheduledTime = clientMedication.scheduledTimes[0]
            val zonedDateTime = scheduledTime.atDate(today)
                .atZone(ZoneId.systemDefault())

            val triggerAtMillis = zonedDateTime.toInstant().toEpochMilli()

            Log.d("AlarmDebug", """
                Setting alarm for:
                Local: ${zonedDateTime.toLocalDateTime()}
                Zone: ${zonedDateTime.zone}
                Millis: $triggerAtMillis
                As Date: ${Date(triggerAtMillis)}
            """.trimIndent())

            // 3. Force-create the intent (for debugging)
            val testIntent = alarmIntent(clientMedication).also {
                Log.d("IntentDebug", "Created PendingIntent: $it")
            }

            // 4. Set the alarm
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                testIntent
            )

            Log.d("AlarmStatus", "Alarm successfully scheduled")

        } catch (e: Exception) {
            Log.e("AlarmScheduler", "Failed to set alarm", e)
        }
    }

    private fun alarmIntent(item: ClientMedication): PendingIntent {
        Log.d("IntentCreation", "Creating intent for ${item.clientMedicationId}")
        return PendingIntent.getBroadcast(
            context,
            item.clientMedicationId.hashCode(),
            Intent(context, AlarmReceiver::class.java).apply {
                action = "com.your.package.ALARM_ACTION"  // Required for Android 8+
                putExtra("EXTRA_MEDICATION_ID", item.clientMedicationId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }


}

private var clientMeds: ClientMedication? = null

fun getClientMedication(): ClientMedication? {
    return clientMeds
}

fun setClientMedication(clientMedication: ClientMedication) {
    clientMeds = clientMedication
}


