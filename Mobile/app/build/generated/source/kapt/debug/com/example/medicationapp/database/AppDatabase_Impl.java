package com.example.medicationapp.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.medicationapp.model.dao.AdherenceLogDao;
import com.example.medicationapp.model.dao.AdherenceLogDao_Impl;
import com.example.medicationapp.model.dao.ClientDao;
import com.example.medicationapp.model.dao.ClientDao_Impl;
import com.example.medicationapp.model.dao.ClientMedicationDao;
import com.example.medicationapp.model.dao.ClientMedicationDao_Impl;
import com.example.medicationapp.model.dao.MedicationDao;
import com.example.medicationapp.model.dao.MedicationDao_Impl;
import com.example.medicationapp.model.dao.MedicationInteractionDao;
import com.example.medicationapp.model.dao.MedicationInteractionDao_Impl;
import com.example.medicationapp.model.dao.MedicationLogDao;
import com.example.medicationapp.model.dao.MedicationLogDao_Impl;
import com.example.medicationapp.model.dao.ReminderDao;
import com.example.medicationapp.model.dao.ReminderDao_Impl;
import com.example.medicationapp.model.dao.ReportDao;
import com.example.medicationapp.model.dao.ReportDao_Impl;
import com.example.medicationapp.model.dao.RoleDao;
import com.example.medicationapp.model.dao.RoleDao_Impl;
import com.example.medicationapp.model.dao.UserDao;
import com.example.medicationapp.model.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile RoleDao _roleDao;

  private volatile ClientDao _clientDao;

  private volatile MedicationDao _medicationDao;

  private volatile MedicationInteractionDao _medicationInteractionDao;

  private volatile ClientMedicationDao _clientMedicationDao;

  private volatile MedicationLogDao _medicationLogDao;

  private volatile ReminderDao _reminderDao;

  private volatile AdherenceLogDao _adherenceLogDao;

  private volatile ReportDao _reportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`userId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `roleId` INTEGER NOT NULL, FOREIGN KEY(`roleId`) REFERENCES `roles`(`roleId`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_users_roleId` ON `users` (`roleId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `roles` (`roleId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `roleName` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_roles_roleName` ON `roles` (`roleName`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `clients` (`clientId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `dob` TEXT NOT NULL, `contactInfo` TEXT NOT NULL, `carerId` INTEGER, `managerId` INTEGER NOT NULL, FOREIGN KEY(`carerId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_clients_carerId` ON `clients` (`carerId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medications` (`medicationId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `sideEffects` TEXT NOT NULL, `interactionInfo` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medication_interactions` (`interactionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `medication_id_1` INTEGER NOT NULL, `medication_id_2` INTEGER NOT NULL, `interactionDescription` TEXT NOT NULL, `severity` TEXT NOT NULL, FOREIGN KEY(`medication_id_1`) REFERENCES `medications`(`medicationId`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`medication_id_2`) REFERENCES `medications`(`medicationId`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_interactions_medication_id_1` ON `medication_interactions` (`medication_id_1`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_interactions_medication_id_2` ON `medication_interactions` (`medication_id_2`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `client_medications` (`clientMedicationId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientId` INTEGER NOT NULL, `medicationId` INTEGER NOT NULL, `dosage` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `isPaused` INTEGER NOT NULL, `scheduledTimes` TEXT NOT NULL, FOREIGN KEY(`clientId`) REFERENCES `clients`(`clientId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`medicationId`) REFERENCES `medications`(`medicationId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_client_medications_clientId` ON `client_medications` (`clientId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_client_medications_medicationId` ON `client_medications` (`medicationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medication_logs` (`logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `client_medication_id` INTEGER NOT NULL, `carerId` INTEGER NOT NULL, `scheduledTime` TEXT NOT NULL, `actualTime` TEXT, `status` TEXT NOT NULL, `notes` TEXT, FOREIGN KEY(`client_medication_id`) REFERENCES `client_medications`(`clientMedicationId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`carerId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_logs_client_medication_id` ON `medication_logs` (`client_medication_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_logs_carerId` ON `medication_logs` (`carerId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`reminderId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientMedicationId` INTEGER NOT NULL, `reminderTime` TEXT NOT NULL, `reminderType` TEXT NOT NULL, FOREIGN KEY(`clientMedicationId`) REFERENCES `client_medications`(`clientMedicationId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_clientMedicationId` ON `reminders` (`clientMedicationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `adherence_logs` (`adherenceId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientMedicationId` INTEGER NOT NULL, `userId` INTEGER NOT NULL, `checkedTime` TEXT NOT NULL, `adherenceRate` REAL NOT NULL, FOREIGN KEY(`clientMedicationId`) REFERENCES `client_medications`(`clientMedicationId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_adherence_logs_clientMedicationId` ON `adherence_logs` (`clientMedicationId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_adherence_logs_userId` ON `adherence_logs` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reports` (`reportId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `carerId` INTEGER NOT NULL, `clientId` INTEGER NOT NULL, `notes` TEXT NOT NULL, `dateCreated` TEXT NOT NULL, FOREIGN KEY(`carerId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`clientId`) REFERENCES `clients`(`clientId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reports_carerId` ON `reports` (`carerId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reports_clientId` ON `reports` (`clientId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4e523c49fc0d16a47a0f4b5c6c271c6b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `roles`");
        db.execSQL("DROP TABLE IF EXISTS `clients`");
        db.execSQL("DROP TABLE IF EXISTS `medications`");
        db.execSQL("DROP TABLE IF EXISTS `medication_interactions`");
        db.execSQL("DROP TABLE IF EXISTS `client_medications`");
        db.execSQL("DROP TABLE IF EXISTS `medication_logs`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        db.execSQL("DROP TABLE IF EXISTS `adherence_logs`");
        db.execSQL("DROP TABLE IF EXISTS `reports`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("userId", new TableInfo.Column("userId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("roleId", new TableInfo.Column("roleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysUsers.add(new TableInfo.ForeignKey("roles", "SET NULL", "NO ACTION", Arrays.asList("roleId"), Arrays.asList("roleId")));
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_roleId", false, Arrays.asList("roleId"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.medicationapp.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsRoles = new HashMap<String, TableInfo.Column>(2);
        _columnsRoles.put("roleId", new TableInfo.Column("roleId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("roleName", new TableInfo.Column("roleName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoles = new HashSet<TableInfo.Index>(1);
        _indicesRoles.add(new TableInfo.Index("index_roles_roleName", true, Arrays.asList("roleName"), Arrays.asList("ASC")));
        final TableInfo _infoRoles = new TableInfo("roles", _columnsRoles, _foreignKeysRoles, _indicesRoles);
        final TableInfo _existingRoles = TableInfo.read(db, "roles");
        if (!_infoRoles.equals(_existingRoles)) {
          return new RoomOpenHelper.ValidationResult(false, "roles(com.example.medicationapp.model.Role).\n"
                  + " Expected:\n" + _infoRoles + "\n"
                  + " Found:\n" + _existingRoles);
        }
        final HashMap<String, TableInfo.Column> _columnsClients = new HashMap<String, TableInfo.Column>(6);
        _columnsClients.put("clientId", new TableInfo.Column("clientId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("dob", new TableInfo.Column("dob", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("contactInfo", new TableInfo.Column("contactInfo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("carerId", new TableInfo.Column("carerId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("managerId", new TableInfo.Column("managerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClients = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysClients.add(new TableInfo.ForeignKey("users", "SET NULL", "NO ACTION", Arrays.asList("carerId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesClients = new HashSet<TableInfo.Index>(1);
        _indicesClients.add(new TableInfo.Index("index_clients_carerId", false, Arrays.asList("carerId"), Arrays.asList("ASC")));
        final TableInfo _infoClients = new TableInfo("clients", _columnsClients, _foreignKeysClients, _indicesClients);
        final TableInfo _existingClients = TableInfo.read(db, "clients");
        if (!_infoClients.equals(_existingClients)) {
          return new RoomOpenHelper.ValidationResult(false, "clients(com.example.medicationapp.model.Client).\n"
                  + " Expected:\n" + _infoClients + "\n"
                  + " Found:\n" + _existingClients);
        }
        final HashMap<String, TableInfo.Column> _columnsMedications = new HashMap<String, TableInfo.Column>(5);
        _columnsMedications.put("medicationId", new TableInfo.Column("medicationId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("sideEffects", new TableInfo.Column("sideEffects", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("interactionInfo", new TableInfo.Column("interactionInfo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedications = new TableInfo("medications", _columnsMedications, _foreignKeysMedications, _indicesMedications);
        final TableInfo _existingMedications = TableInfo.read(db, "medications");
        if (!_infoMedications.equals(_existingMedications)) {
          return new RoomOpenHelper.ValidationResult(false, "medications(com.example.medicationapp.model.Medication).\n"
                  + " Expected:\n" + _infoMedications + "\n"
                  + " Found:\n" + _existingMedications);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicationInteractions = new HashMap<String, TableInfo.Column>(5);
        _columnsMedicationInteractions.put("interactionId", new TableInfo.Column("interactionId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationInteractions.put("medication_id_1", new TableInfo.Column("medication_id_1", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationInteractions.put("medication_id_2", new TableInfo.Column("medication_id_2", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationInteractions.put("interactionDescription", new TableInfo.Column("interactionDescription", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationInteractions.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicationInteractions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysMedicationInteractions.add(new TableInfo.ForeignKey("medications", "NO ACTION", "NO ACTION", Arrays.asList("medication_id_1"), Arrays.asList("medicationId")));
        _foreignKeysMedicationInteractions.add(new TableInfo.ForeignKey("medications", "NO ACTION", "NO ACTION", Arrays.asList("medication_id_2"), Arrays.asList("medicationId")));
        final HashSet<TableInfo.Index> _indicesMedicationInteractions = new HashSet<TableInfo.Index>(2);
        _indicesMedicationInteractions.add(new TableInfo.Index("index_medication_interactions_medication_id_1", false, Arrays.asList("medication_id_1"), Arrays.asList("ASC")));
        _indicesMedicationInteractions.add(new TableInfo.Index("index_medication_interactions_medication_id_2", false, Arrays.asList("medication_id_2"), Arrays.asList("ASC")));
        final TableInfo _infoMedicationInteractions = new TableInfo("medication_interactions", _columnsMedicationInteractions, _foreignKeysMedicationInteractions, _indicesMedicationInteractions);
        final TableInfo _existingMedicationInteractions = TableInfo.read(db, "medication_interactions");
        if (!_infoMedicationInteractions.equals(_existingMedicationInteractions)) {
          return new RoomOpenHelper.ValidationResult(false, "medication_interactions(com.example.medicationapp.model.MedicationInteraction).\n"
                  + " Expected:\n" + _infoMedicationInteractions + "\n"
                  + " Found:\n" + _existingMedicationInteractions);
        }
        final HashMap<String, TableInfo.Column> _columnsClientMedications = new HashMap<String, TableInfo.Column>(8);
        _columnsClientMedications.put("clientMedicationId", new TableInfo.Column("clientMedicationId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("clientId", new TableInfo.Column("clientId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("medicationId", new TableInfo.Column("medicationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("dosage", new TableInfo.Column("dosage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("isPaused", new TableInfo.Column("isPaused", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientMedications.put("scheduledTimes", new TableInfo.Column("scheduledTimes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClientMedications = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysClientMedications.add(new TableInfo.ForeignKey("clients", "CASCADE", "NO ACTION", Arrays.asList("clientId"), Arrays.asList("clientId")));
        _foreignKeysClientMedications.add(new TableInfo.ForeignKey("medications", "CASCADE", "NO ACTION", Arrays.asList("medicationId"), Arrays.asList("medicationId")));
        final HashSet<TableInfo.Index> _indicesClientMedications = new HashSet<TableInfo.Index>(2);
        _indicesClientMedications.add(new TableInfo.Index("index_client_medications_clientId", false, Arrays.asList("clientId"), Arrays.asList("ASC")));
        _indicesClientMedications.add(new TableInfo.Index("index_client_medications_medicationId", false, Arrays.asList("medicationId"), Arrays.asList("ASC")));
        final TableInfo _infoClientMedications = new TableInfo("client_medications", _columnsClientMedications, _foreignKeysClientMedications, _indicesClientMedications);
        final TableInfo _existingClientMedications = TableInfo.read(db, "client_medications");
        if (!_infoClientMedications.equals(_existingClientMedications)) {
          return new RoomOpenHelper.ValidationResult(false, "client_medications(com.example.medicationapp.model.ClientMedication).\n"
                  + " Expected:\n" + _infoClientMedications + "\n"
                  + " Found:\n" + _existingClientMedications);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicationLogs = new HashMap<String, TableInfo.Column>(7);
        _columnsMedicationLogs.put("logId", new TableInfo.Column("logId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("client_medication_id", new TableInfo.Column("client_medication_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("carerId", new TableInfo.Column("carerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("scheduledTime", new TableInfo.Column("scheduledTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("actualTime", new TableInfo.Column("actualTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicationLogs = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysMedicationLogs.add(new TableInfo.ForeignKey("client_medications", "CASCADE", "NO ACTION", Arrays.asList("client_medication_id"), Arrays.asList("clientMedicationId")));
        _foreignKeysMedicationLogs.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("carerId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesMedicationLogs = new HashSet<TableInfo.Index>(2);
        _indicesMedicationLogs.add(new TableInfo.Index("index_medication_logs_client_medication_id", false, Arrays.asList("client_medication_id"), Arrays.asList("ASC")));
        _indicesMedicationLogs.add(new TableInfo.Index("index_medication_logs_carerId", false, Arrays.asList("carerId"), Arrays.asList("ASC")));
        final TableInfo _infoMedicationLogs = new TableInfo("medication_logs", _columnsMedicationLogs, _foreignKeysMedicationLogs, _indicesMedicationLogs);
        final TableInfo _existingMedicationLogs = TableInfo.read(db, "medication_logs");
        if (!_infoMedicationLogs.equals(_existingMedicationLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "medication_logs(com.example.medicationapp.model.MedicationLog).\n"
                  + " Expected:\n" + _infoMedicationLogs + "\n"
                  + " Found:\n" + _existingMedicationLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(4);
        _columnsReminders.put("reminderId", new TableInfo.Column("reminderId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("clientMedicationId", new TableInfo.Column("clientMedicationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("reminderTime", new TableInfo.Column("reminderTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("reminderType", new TableInfo.Column("reminderType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReminders.add(new TableInfo.ForeignKey("client_medications", "CASCADE", "NO ACTION", Arrays.asList("clientMedicationId"), Arrays.asList("clientMedicationId")));
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(1);
        _indicesReminders.add(new TableInfo.Index("index_reminders_clientMedicationId", false, Arrays.asList("clientMedicationId"), Arrays.asList("ASC")));
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.example.medicationapp.model.Reminder).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        final HashMap<String, TableInfo.Column> _columnsAdherenceLogs = new HashMap<String, TableInfo.Column>(5);
        _columnsAdherenceLogs.put("adherenceId", new TableInfo.Column("adherenceId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdherenceLogs.put("clientMedicationId", new TableInfo.Column("clientMedicationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdherenceLogs.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdherenceLogs.put("checkedTime", new TableInfo.Column("checkedTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdherenceLogs.put("adherenceRate", new TableInfo.Column("adherenceRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAdherenceLogs = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysAdherenceLogs.add(new TableInfo.ForeignKey("client_medications", "CASCADE", "NO ACTION", Arrays.asList("clientMedicationId"), Arrays.asList("clientMedicationId")));
        _foreignKeysAdherenceLogs.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesAdherenceLogs = new HashSet<TableInfo.Index>(2);
        _indicesAdherenceLogs.add(new TableInfo.Index("index_adherence_logs_clientMedicationId", false, Arrays.asList("clientMedicationId"), Arrays.asList("ASC")));
        _indicesAdherenceLogs.add(new TableInfo.Index("index_adherence_logs_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoAdherenceLogs = new TableInfo("adherence_logs", _columnsAdherenceLogs, _foreignKeysAdherenceLogs, _indicesAdherenceLogs);
        final TableInfo _existingAdherenceLogs = TableInfo.read(db, "adherence_logs");
        if (!_infoAdherenceLogs.equals(_existingAdherenceLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "adherence_logs(com.example.medicationapp.model.AdherenceLog).\n"
                  + " Expected:\n" + _infoAdherenceLogs + "\n"
                  + " Found:\n" + _existingAdherenceLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsReports = new HashMap<String, TableInfo.Column>(5);
        _columnsReports.put("reportId", new TableInfo.Column("reportId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("carerId", new TableInfo.Column("carerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("clientId", new TableInfo.Column("clientId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("dateCreated", new TableInfo.Column("dateCreated", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReports = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysReports.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("carerId"), Arrays.asList("userId")));
        _foreignKeysReports.add(new TableInfo.ForeignKey("clients", "CASCADE", "NO ACTION", Arrays.asList("clientId"), Arrays.asList("clientId")));
        final HashSet<TableInfo.Index> _indicesReports = new HashSet<TableInfo.Index>(2);
        _indicesReports.add(new TableInfo.Index("index_reports_carerId", false, Arrays.asList("carerId"), Arrays.asList("ASC")));
        _indicesReports.add(new TableInfo.Index("index_reports_clientId", false, Arrays.asList("clientId"), Arrays.asList("ASC")));
        final TableInfo _infoReports = new TableInfo("reports", _columnsReports, _foreignKeysReports, _indicesReports);
        final TableInfo _existingReports = TableInfo.read(db, "reports");
        if (!_infoReports.equals(_existingReports)) {
          return new RoomOpenHelper.ValidationResult(false, "reports(com.example.medicationapp.model.Report).\n"
                  + " Expected:\n" + _infoReports + "\n"
                  + " Found:\n" + _existingReports);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4e523c49fc0d16a47a0f4b5c6c271c6b", "357a85449dd837e802bb67e512b53b65");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","roles","clients","medications","medication_interactions","client_medications","medication_logs","reminders","adherence_logs","reports");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `roles`");
      _db.execSQL("DELETE FROM `clients`");
      _db.execSQL("DELETE FROM `medication_interactions`");
      _db.execSQL("DELETE FROM `medications`");
      _db.execSQL("DELETE FROM `client_medications`");
      _db.execSQL("DELETE FROM `medication_logs`");
      _db.execSQL("DELETE FROM `reminders`");
      _db.execSQL("DELETE FROM `adherence_logs`");
      _db.execSQL("DELETE FROM `reports`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RoleDao.class, RoleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ClientDao.class, ClientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationDao.class, MedicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationInteractionDao.class, MedicationInteractionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ClientMedicationDao.class, ClientMedicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationLogDao.class, MedicationLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AdherenceLogDao.class, AdherenceLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReportDao.class, ReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public RoleDao roleDao() {
    if (_roleDao != null) {
      return _roleDao;
    } else {
      synchronized(this) {
        if(_roleDao == null) {
          _roleDao = new RoleDao_Impl(this);
        }
        return _roleDao;
      }
    }
  }

  @Override
  public ClientDao clientDao() {
    if (_clientDao != null) {
      return _clientDao;
    } else {
      synchronized(this) {
        if(_clientDao == null) {
          _clientDao = new ClientDao_Impl(this);
        }
        return _clientDao;
      }
    }
  }

  @Override
  public MedicationDao medicationDao() {
    if (_medicationDao != null) {
      return _medicationDao;
    } else {
      synchronized(this) {
        if(_medicationDao == null) {
          _medicationDao = new MedicationDao_Impl(this);
        }
        return _medicationDao;
      }
    }
  }

  @Override
  public MedicationInteractionDao medicationInteractionDao() {
    if (_medicationInteractionDao != null) {
      return _medicationInteractionDao;
    } else {
      synchronized(this) {
        if(_medicationInteractionDao == null) {
          _medicationInteractionDao = new MedicationInteractionDao_Impl(this);
        }
        return _medicationInteractionDao;
      }
    }
  }

  @Override
  public ClientMedicationDao clientMedicationDao() {
    if (_clientMedicationDao != null) {
      return _clientMedicationDao;
    } else {
      synchronized(this) {
        if(_clientMedicationDao == null) {
          _clientMedicationDao = new ClientMedicationDao_Impl(this);
        }
        return _clientMedicationDao;
      }
    }
  }

  @Override
  public MedicationLogDao medicationLogDao() {
    if (_medicationLogDao != null) {
      return _medicationLogDao;
    } else {
      synchronized(this) {
        if(_medicationLogDao == null) {
          _medicationLogDao = new MedicationLogDao_Impl(this);
        }
        return _medicationLogDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }

  @Override
  public AdherenceLogDao adherenceLogDao() {
    if (_adherenceLogDao != null) {
      return _adherenceLogDao;
    } else {
      synchronized(this) {
        if(_adherenceLogDao == null) {
          _adherenceLogDao = new AdherenceLogDao_Impl(this);
        }
        return _adherenceLogDao;
      }
    }
  }

  @Override
  public ReportDao reportDao() {
    if (_reportDao != null) {
      return _reportDao;
    } else {
      synchronized(this) {
        if(_reportDao == null) {
          _reportDao = new ReportDao_Impl(this);
        }
        return _reportDao;
      }
    }
  }
}
