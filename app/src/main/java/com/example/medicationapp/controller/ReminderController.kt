package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Reminder

class ReminderController(context: Context) {
    private val reminderDao = AppDatabase.getDatabase(context).reminderDao()

    suspend fun createReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    suspend fun getAllReminders(): List<Reminder> {
        return reminderDao.getAllReminders()
    }
    }
