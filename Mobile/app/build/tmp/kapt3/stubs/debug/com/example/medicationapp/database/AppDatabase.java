package com.example.medicationapp.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&\u00a8\u0006\u0018"}, d2 = {"Lcom/example/medicationapp/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "adherenceLogDao", "Lcom/example/medicationapp/model/dao/AdherenceLogDao;", "clientDao", "Lcom/example/medicationapp/model/dao/ClientDao;", "clientMedicationDao", "Lcom/example/medicationapp/model/dao/ClientMedicationDao;", "medicationDao", "Lcom/example/medicationapp/model/dao/MedicationDao;", "medicationInteractionDao", "Lcom/example/medicationapp/model/dao/MedicationInteractionDao;", "medicationLogDao", "Lcom/example/medicationapp/model/dao/MedicationLogDao;", "reminderDao", "Lcom/example/medicationapp/model/dao/ReminderDao;", "reportDao", "Lcom/example/medicationapp/model/dao/ReportDao;", "roleDao", "Lcom/example/medicationapp/model/dao/RoleDao;", "userDao", "Lcom/example/medicationapp/model/dao/UserDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.example.medicationapp.model.User.class, com.example.medicationapp.model.Role.class, com.example.medicationapp.model.Client.class, com.example.medicationapp.model.Medication.class, com.example.medicationapp.model.MedicationInteraction.class, com.example.medicationapp.model.ClientMedication.class, com.example.medicationapp.model.MedicationLog.class, com.example.medicationapp.model.Reminder.class, com.example.medicationapp.model.AdherenceLog.class, com.example.medicationapp.model.Report.class}, version = 2, exportSchema = false)
@androidx.room.TypeConverters(value = {com.example.medicationapp.database.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.medicationapp.database.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.medicationapp.database.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.RoleDao roleDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.ClientDao clientDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.MedicationDao medicationDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.MedicationInteractionDao medicationInteractionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.ClientMedicationDao clientMedicationDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.MedicationLogDao medicationLogDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.ReminderDao reminderDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.AdherenceLogDao adherenceLogDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.medicationapp.model.dao.ReportDao reportDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0083@\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/medicationapp/database/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/example/medicationapp/database/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "seedInitialData", "", "db", "(Lcom/example/medicationapp/database/AppDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "AppDatabaseCallback", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.medicationapp.database.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
        private final java.lang.Object seedInitialData(com.example.medicationapp.database.AppDatabase db, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017\u00a8\u0006\u0007"}, d2 = {"Lcom/example/medicationapp/database/AppDatabase$Companion$AppDatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "()V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "app_debug"})
        static final class AppDatabaseCallback extends androidx.room.RoomDatabase.Callback {
            
            public AppDatabaseCallback() {
                super();
            }
            
            @java.lang.Override()
            @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
            public void onCreate(@org.jetbrains.annotations.NotNull()
            androidx.sqlite.db.SupportSQLiteDatabase db) {
            }
        }
    }
}