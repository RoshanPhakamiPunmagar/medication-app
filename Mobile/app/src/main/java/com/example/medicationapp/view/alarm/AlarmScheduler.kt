package com.example.medicationapp.view.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

            //setting Client Medication
            setClientMedication(clientMedication)

            //Calculate trigger time
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

            // 3. Force-create the intent
            val testIntent = createAlarmPendingIntent(clientMedication,clientMedication.clientMedicationId.hashCode())

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

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun setAlarm(clientMedication: ClientMedication, triggerAtMillis: Long) {
        try {
            val requestCode = (clientMedication.clientMedicationId.toString() + triggerAtMillis.toString()).hashCode()
            val pendingIntent = createAlarmPendingIntent(clientMedication, requestCode)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }

            Log.d("AlarmSet", "Alarm set for ${Date(triggerAtMillis)}")
        } catch (e: Exception) {
            Log.e("AlarmError", "Failed to set alarm", e)
        }
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun setUpAlarmDateRange(clientMedication: ClientMedication) {
        try {
            if (clientMedication.scheduledTimes.isEmpty()) {
                Log.e("AlarmError", "No scheduled times provided")
                return
            }
            //seting client medication
            setClientMedication(clientMedication)

            val startDate = clientMedication.startDate
            val endDate = clientMedication.endDate
            var currentDate = startDate

            // Manual date range iteration for API < 26 compatibility
            while (!currentDate.isAfter(endDate)) {
                clientMedication.scheduledTimes.forEach { time ->
                    val triggerAtMillis = time.atDate(currentDate)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()

                    if (triggerAtMillis > System.currentTimeMillis()) {
                        setAlarm(clientMedication, triggerAtMillis)
                    }
                }
                currentDate = currentDate.plusDays(1)
            }

            Log.d("AlarmStatus", "Alarms scheduled successfully")
        } catch (e: Exception) {
            Log.e("AlarmError", "Failed to schedule alarms", e)
        }
    }

    private fun createAlarmPendingIntent(clientMedication: ClientMedication, requestCode: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            Intent(context, AlarmReceiver::class.java).apply {
                action = "com.example.medicationapp.ALARM_ACTION"
                putExtra("medication", clientMedication)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.FLAG_IMMUTABLE
                    } else {
                        0
                    })
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


