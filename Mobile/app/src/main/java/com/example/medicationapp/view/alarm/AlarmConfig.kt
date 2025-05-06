package com.example.medicationapp.view.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

object AlarmConfig {
    var ringtone: Ringtone? = null

    fun play(context: Context) {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone?.play()
    }

    fun stop() {
        ringtone?.stop()
    }
}