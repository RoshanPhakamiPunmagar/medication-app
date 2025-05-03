package com.example.medicationapp.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.medicationapp.database.Converters;
import com.example.medicationapp.model.MedicationLog;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
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
public final class MedicationLogDao_Impl implements MedicationLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicationLog> __insertionAdapterOfMedicationLog;

  private final Converters __converters = new Converters();

  public MedicationLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicationLog = new EntityInsertionAdapter<MedicationLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medication_logs` (`logId`,`client_medication_id`,`carerId`,`scheduledTime`,`actualTime`,`status`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationLog entity) {
        statement.bindLong(1, entity.getLogId());
        statement.bindLong(2, entity.getClientMedicationId());
        statement.bindLong(3, entity.getCarerId());
        final String _tmp = __converters.fromLocalTimeListToString(entity.getScheduledTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalTimeToString(entity.getActualTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindString(6, __Status_enumToString(entity.getStatus()));
        if (entity.getNotes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNotes());
        }
      }
    };
  }

  @Override
  public Object insertLog(final MedicationLog log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicationLog.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLogsForClientMedication(final int cmId,
      final Continuation<? super List<MedicationLog>> $completion) {
    final String _sql = "SELECT * FROM medication_logs WHERE client_medication_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cmId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MedicationLog>>() {
      @Override
      @NonNull
      public List<MedicationLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "logId");
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "client_medication_id");
          final int _cursorIndexOfCarerId = CursorUtil.getColumnIndexOrThrow(_cursor, "carerId");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfActualTime = CursorUtil.getColumnIndexOrThrow(_cursor, "actualTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicationLog> _result = new ArrayList<MedicationLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationLog _item;
            final long _tmpLogId;
            _tmpLogId = _cursor.getLong(_cursorIndexOfLogId);
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpCarerId;
            _tmpCarerId = _cursor.getLong(_cursorIndexOfCarerId);
            final List<LocalTime> _tmpScheduledTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfScheduledTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfScheduledTime);
            }
            _tmpScheduledTime = __converters.fromStringToLocalTimeList(_tmp);
            final LocalTime _tmpActualTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActualTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActualTime);
            }
            _tmpActualTime = __converters.fromStringToLocalTime(_tmp_1);
            final MedicationLog.Status _tmpStatus;
            _tmpStatus = __Status_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new MedicationLog(_tmpLogId,_tmpClientMedicationId,_tmpCarerId,_tmpScheduledTime,_tmpActualTime,_tmpStatus,_tmpNotes);
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

  private String __Status_enumToString(@NonNull final MedicationLog.Status _value) {
    switch (_value) {
      case Given: return "Given";
      case Skipped: return "Skipped";
      case Missed: return "Missed";
      case Late: return "Late";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private MedicationLog.Status __Status_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "Given": return MedicationLog.Status.Given;
      case "Skipped": return MedicationLog.Status.Skipped;
      case "Missed": return MedicationLog.Status.Missed;
      case "Late": return MedicationLog.Status.Late;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
