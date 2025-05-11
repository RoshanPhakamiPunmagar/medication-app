package com.example.medicationapp.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.medicationapp.model.*
import com.example.medicationapp.model.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

@Database(
    entities = [
        User::class,
        Role::class,
        Client::class,
        Medication::class,
        MedicationInteraction::class,
        ClientMedication::class,
        MedicationLog::class,
        Reminder::class,
        AdherenceLog::class,
        Report::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun roleDao(): RoleDao
    abstract fun clientDao(): ClientDao
    abstract fun medicationDao(): MedicationDao
    abstract fun medicationInteractionDao(): MedicationInteractionDao
    abstract fun clientMedicationDao(): ClientMedicationDao
    abstract fun medicationLogDao(): MedicationLogDao
    abstract fun reminderDao(): ReminderDao
    abstract fun adherenceLogDao(): AdherenceLogDao
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medication_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {

                        seedInitialData(database)
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun seedInitialData(db: AppDatabase) {
            val roleDao = db.roleDao()
            val userDao = db.userDao()


            if (roleDao.getAllRoles().isEmpty()) {
                roleDao.insertRole(Role(1, "Manager"))
                roleDao.insertRole(Role(2, "Carer"))
            }

            var manager = userDao.getUserByEmail("manager@app.com")
            if (manager == null) {
                userDao.insertUser(
                    User(
                        name = "Admin User",
                        email = "manager@app.com",
                        password = "password123",
                        roleId = 1
                    )
                )
            } else {
                manager.userId
            }

        }
    }
}