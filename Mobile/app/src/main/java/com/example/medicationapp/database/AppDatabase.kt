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
            val clientDao = db.clientDao()
            val medicationDao = db.medicationDao()


            if (roleDao.getAllRoles().isEmpty()) {
                roleDao.insertRole(Role(1, "Manager"))
                roleDao.insertRole(Role(2, "Carer"))
            }

            var manager = userDao.getUserByEmail("manager@app.com")
            val managerId = if (manager == null) {
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

            // Seed clients only if they are empty
            if (clientDao.getAllClients().isEmpty()) {
                clientDao.insertClient(
                    Client(
                        name = "Roshan Magar",
                        dob = "1940-01-15",
                        contactInfo = "555‑0101",
                        carerId = null,
                        managerId = managerId
                    )
                )
                clientDao.insertClient(
                    Client(
                        name = "Namrata Magar",
                        dob = "1935-06-07",
                        contactInfo = "555‑0202",
                        carerId = null,
                        managerId = managerId
                    )
                )
                clientDao.insertClient(
                    Client(
                        name = "Lisa MoonLit",
                        dob = "1950-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )

                clientDao.insertClient(
                    Client(
                        name = "Lisa MoonLit",
                        dob = "2001-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )

                clientDao.insertClient(
                    Client(
                        name = "Lisa MooLit",
                        dob = "1950-11-05",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )

                clientDao.insertClient(
                    Client(
                        name = "Lisa Mot",
                        dob = "1950-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )

                clientDao.insertClient(
                    Client(
                        name = "Lisa MoonLit",
                        dob = "1250-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )

                clientDao.insertClient(
                    Client(
                        name = "La MoonLit",
                        dob = "1950-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )
                clientDao.insertClient(
                    Client(
                        name = "Lisa MoonLit",
                        dob = "1950-01-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )
                clientDao.insertClient(
                    Client(
                        name = "Lisa MoLit",
                        dob = "1950-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )
                clientDao.insertClient(
                    Client(
                        name = "Lsa MoonLit",
                        dob = "1950-11-30",
                        contactInfo = "555‑0303",
                        carerId = null,
                        managerId = managerId
                    )
                )
            }

            // Seed medications
            if (medicationDao.getAllMedications().isEmpty()) {
                medicationDao.insertMedication(
                    Medication(
                        name = "Paracetamol",
                        description = "Pain reliever and fever reducer",
                        sideEffects = "Nausea, rash, liver damage with overdose",
                        interactionInfo = "May interact with alcohol or warfarin"
                    )
                )
                medicationDao.insertMedication(
                    Medication(
                        name = "Atorvastatin",
                        description = "Used to lower cholesterol levels",
                        sideEffects = "Muscle pain, liver enzyme abnormalities",
                        interactionInfo = "May interact with grapefruit juice and antibiotics"
                    )
                )
                medicationDao.insertMedication(
                    Medication(
                        name = "Amlodipine",
                        description = "Used to treat high blood pressure and angina",
                        sideEffects = "Swelling, dizziness, fatigue",
                        interactionInfo = "May interact with simvastatin and beta-blockers"
                    )
                )
                medicationDao.insertMedication(
                    Medication(
                        name = "Metformin",
                        description = "First-line medication for type 2 diabetes",
                        sideEffects = "Diarrhea, nausea, abdominal pain, lactic acidosis (rare)",
                        interactionInfo = "May interact with alcohol and contrast dye"
                    )
                )
                medicationDao.insertMedication(
                    Medication(
                        name = "Omeprazole",
                        description = "Reduces stomach acid, treats GERD and ulcers",
                        sideEffects = "Headache, diarrhea, vitamin B12 deficiency with long-term use",
                        interactionInfo = "May interact with clopidogrel and some antifungals"
                    )
                )

            }
        }
    }
}