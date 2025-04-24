package com.example.medicationapp.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationapp.model.Reminder

import androidx.room.Update

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE clientMedicationId = :cmId")
    suspend fun getRemindersForClientMedication(cmId: Int): List<Reminder>

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<Reminder> // <-- Add this!
}
