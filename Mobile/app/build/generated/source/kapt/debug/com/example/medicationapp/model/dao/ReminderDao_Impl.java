package com.example.medicationapp.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.medicationapp.database.Converters;
import com.example.medicationapp.model.Reminder;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reminder> __insertionAdapterOfReminder;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Reminder> __deletionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __updateAdapterOfReminder;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminder = new EntityInsertionAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reminders` (`reminderId`,`clientMedicationId`,`reminderTime`,`reminderType`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getReminderId());
        statement.bindLong(2, entity.getClientMedicationId());
        final String _tmp = __converters.fromLocalTimeToString(entity.getReminderTime());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getReminderType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getReminderType());
        }
      }
    };
    this.__deletionAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminders` WHERE `reminderId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getReminderId());
      }
    };
    this.__updateAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `reminderId` = ?,`clientMedicationId` = ?,`reminderTime` = ?,`reminderType` = ? WHERE `reminderId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getReminderId());
        statement.bindLong(2, entity.getClientMedicationId());
        final String _tmp = __converters.fromLocalTimeToString(entity.getReminderTime());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        if (entity.getReminderType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getReminderType());
        }
        statement.bindLong(5, entity.getReminderId());
      }
    };
  }

  @Override
  public Object insertReminder(final Reminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReminder.insert(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReminder(final Reminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReminder(final Reminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRemindersForClientMedication(final int cmId,
      final Continuation<? super List<Reminder>> $completion) {
    final String _sql = "SELECT * FROM reminders WHERE clientMedicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cmId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReminderId = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderId");
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpReminderId;
            _tmpReminderId = _cursor.getLong(_cursorIndexOfReminderId);
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final LocalTime _tmpReminderTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = __converters.fromStringToLocalTime(_tmp);
            final String _tmpReminderType;
            if (_cursor.isNull(_cursorIndexOfReminderType)) {
              _tmpReminderType = null;
            } else {
              _tmpReminderType = _cursor.getString(_cursorIndexOfReminderType);
            }
            _item = new Reminder(_tmpReminderId,_tmpClientMedicationId,_tmpReminderTime,_tmpReminderType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllReminders(final Continuation<? super List<Reminder>> $completion) {
    final String _sql = "SELECT * FROM reminders";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReminderId = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderId");
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpReminderId;
            _tmpReminderId = _cursor.getLong(_cursorIndexOfReminderId);
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final LocalTime _tmpReminderTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = __converters.fromStringToLocalTime(_tmp);
            final String _tmpReminderType;
            if (_cursor.isNull(_cursorIndexOfReminderType)) {
              _tmpReminderType = null;
            } else {
              _tmpReminderType = _cursor.getString(_cursorIndexOfReminderType);
            }
            _item = new Reminder(_tmpReminderId,_tmpClientMedicationId,_tmpReminderTime,_tmpReminderType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
