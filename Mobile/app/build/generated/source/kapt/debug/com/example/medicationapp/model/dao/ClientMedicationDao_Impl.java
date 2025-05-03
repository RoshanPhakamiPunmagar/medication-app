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
import com.example.medicationapp.model.ClientMedication;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDate;
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
public final class ClientMedicationDao_Impl implements ClientMedicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ClientMedication> __insertionAdapterOfClientMedication;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ClientMedication> __deletionAdapterOfClientMedication;

  private final EntityDeletionOrUpdateAdapter<ClientMedication> __updateAdapterOfClientMedication;

  public ClientMedicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfClientMedication = new EntityInsertionAdapter<ClientMedication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `client_medications` (`clientMedicationId`,`clientId`,`medicationId`,`dosage`,`startDate`,`endDate`,`isPaused`,`scheduledTimes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClientMedication entity) {
        statement.bindLong(1, entity.getClientMedicationId());
        statement.bindLong(2, entity.getClientId());
        statement.bindLong(3, entity.getMedicationId());
        if (entity.getDosage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDosage());
        }
        final String _tmp = __converters.fromLocalDateToString(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDateToString(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        final int _tmp_2 = entity.isPaused() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        final String _tmp_3 = __converters.fromLocalTimeListToString(entity.getScheduledTimes());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
      }
    };
    this.__deletionAdapterOfClientMedication = new EntityDeletionOrUpdateAdapter<ClientMedication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `client_medications` WHERE `clientMedicationId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClientMedication entity) {
        statement.bindLong(1, entity.getClientMedicationId());
      }
    };
    this.__updateAdapterOfClientMedication = new EntityDeletionOrUpdateAdapter<ClientMedication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `client_medications` SET `clientMedicationId` = ?,`clientId` = ?,`medicationId` = ?,`dosage` = ?,`startDate` = ?,`endDate` = ?,`isPaused` = ?,`scheduledTimes` = ? WHERE `clientMedicationId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClientMedication entity) {
        statement.bindLong(1, entity.getClientMedicationId());
        statement.bindLong(2, entity.getClientId());
        statement.bindLong(3, entity.getMedicationId());
        if (entity.getDosage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDosage());
        }
        final String _tmp = __converters.fromLocalDateToString(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDateToString(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        final int _tmp_2 = entity.isPaused() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        final String _tmp_3 = __converters.fromLocalTimeListToString(entity.getScheduledTimes());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
        statement.bindLong(9, entity.getClientMedicationId());
      }
    };
  }

  @Override
  public Object insertClientMedication(final ClientMedication medication,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfClientMedication.insertAndReturnId(medication);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteClientMedication(final ClientMedication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfClientMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateClientMedication(final ClientMedication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfClientMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMedicationsForClient(final long clientId,
      final Continuation<? super List<ClientMedication>> $completion) {
    final String _sql = "SELECT * FROM client_medications WHERE clientId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clientId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ClientMedication>>() {
      @Override
      @NonNull
      public List<ClientMedication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfScheduledTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimes");
          final List<ClientMedication> _result = new ArrayList<ClientMedication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClientMedication _item;
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final String _tmpDosage;
            if (_cursor.isNull(_cursorIndexOfDosage)) {
              _tmpDosage = null;
            } else {
              _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __converters.fromStringToLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromStringToLocalDate(_tmp_1);
            final boolean _tmpIsPaused;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_2 != 0;
            final List<LocalTime> _tmpScheduledTimes;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfScheduledTimes)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfScheduledTimes);
            }
            _tmpScheduledTimes = __converters.fromStringToLocalTimeList(_tmp_3);
            _item = new ClientMedication(_tmpClientMedicationId,_tmpClientId,_tmpMedicationId,_tmpDosage,_tmpStartDate,_tmpEndDate,_tmpIsPaused,_tmpScheduledTimes);
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
  public Object getClientMedicationById(final long id,
      final Continuation<? super ClientMedication> $completion) {
    final String _sql = "SELECT * FROM client_medications WHERE medicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ClientMedication>() {
      @Override
      @NonNull
      public ClientMedication call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfScheduledTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimes");
          final ClientMedication _result;
          if (_cursor.moveToFirst()) {
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final String _tmpDosage;
            if (_cursor.isNull(_cursorIndexOfDosage)) {
              _tmpDosage = null;
            } else {
              _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __converters.fromStringToLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromStringToLocalDate(_tmp_1);
            final boolean _tmpIsPaused;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_2 != 0;
            final List<LocalTime> _tmpScheduledTimes;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfScheduledTimes)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfScheduledTimes);
            }
            _tmpScheduledTimes = __converters.fromStringToLocalTimeList(_tmp_3);
            _result = new ClientMedication(_tmpClientMedicationId,_tmpClientId,_tmpMedicationId,_tmpDosage,_tmpStartDate,_tmpEndDate,_tmpIsPaused,_tmpScheduledTimes);
          } else {
            _result = null;
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
  public Object getAllClientMedications(
      final Continuation<? super List<ClientMedication>> $completion) {
    final String _sql = "SELECT * FROM client_medications";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ClientMedication>>() {
      @Override
      @NonNull
      public List<ClientMedication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfScheduledTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimes");
          final List<ClientMedication> _result = new ArrayList<ClientMedication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClientMedication _item;
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final String _tmpDosage;
            if (_cursor.isNull(_cursorIndexOfDosage)) {
              _tmpDosage = null;
            } else {
              _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __converters.fromStringToLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromStringToLocalDate(_tmp_1);
            final boolean _tmpIsPaused;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_2 != 0;
            final List<LocalTime> _tmpScheduledTimes;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfScheduledTimes)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfScheduledTimes);
            }
            _tmpScheduledTimes = __converters.fromStringToLocalTimeList(_tmp_3);
            _item = new ClientMedication(_tmpClientMedicationId,_tmpClientId,_tmpMedicationId,_tmpDosage,_tmpStartDate,_tmpEndDate,_tmpIsPaused,_tmpScheduledTimes);
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
  public Object getClientsForMedication(final int medicationId,
      final Continuation<? super List<ClientMedication>> $completion) {
    final String _sql = "SELECT * FROM client_medications WHERE medicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, medicationId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ClientMedication>>() {
      @Override
      @NonNull
      public List<ClientMedication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfScheduledTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimes");
          final List<ClientMedication> _result = new ArrayList<ClientMedication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClientMedication _item;
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final String _tmpDosage;
            if (_cursor.isNull(_cursorIndexOfDosage)) {
              _tmpDosage = null;
            } else {
              _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __converters.fromStringToLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromStringToLocalDate(_tmp_1);
            final boolean _tmpIsPaused;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_2 != 0;
            final List<LocalTime> _tmpScheduledTimes;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfScheduledTimes)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfScheduledTimes);
            }
            _tmpScheduledTimes = __converters.fromStringToLocalTimeList(_tmp_3);
            _item = new ClientMedication(_tmpClientMedicationId,_tmpClientId,_tmpMedicationId,_tmpDosage,_tmpStartDate,_tmpEndDate,_tmpIsPaused,_tmpScheduledTimes);
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
  public Object getClientsMedicationById(final int clientMedicationId,
      final Continuation<? super ClientMedication> $completion) {
    final String _sql = "SELECT * FROM client_medications WHERE clientMedicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clientMedicationId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ClientMedication>() {
      @Override
      @NonNull
      public ClientMedication call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfScheduledTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTimes");
          final ClientMedication _result;
          if (_cursor.moveToFirst()) {
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final String _tmpDosage;
            if (_cursor.isNull(_cursorIndexOfDosage)) {
              _tmpDosage = null;
            } else {
              _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __converters.fromStringToLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromStringToLocalDate(_tmp_1);
            final boolean _tmpIsPaused;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_2 != 0;
            final List<LocalTime> _tmpScheduledTimes;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfScheduledTimes)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfScheduledTimes);
            }
            _tmpScheduledTimes = __converters.fromStringToLocalTimeList(_tmp_3);
            _result = new ClientMedication(_tmpClientMedicationId,_tmpClientId,_tmpMedicationId,_tmpDosage,_tmpStartDate,_tmpEndDate,_tmpIsPaused,_tmpScheduledTimes);
          } else {
            _result = null;
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
