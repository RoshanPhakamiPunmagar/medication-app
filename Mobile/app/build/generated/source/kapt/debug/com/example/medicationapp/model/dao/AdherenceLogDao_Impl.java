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
import com.example.medicationapp.model.AdherenceLog;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AdherenceLogDao_Impl implements AdherenceLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AdherenceLog> __insertionAdapterOfAdherenceLog;

  private final Converters __converters = new Converters();

  public AdherenceLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAdherenceLog = new EntityInsertionAdapter<AdherenceLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `adherence_logs` (`adherenceId`,`clientMedicationId`,`userId`,`checkedTime`,`adherenceRate`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AdherenceLog entity) {
        statement.bindLong(1, entity.getAdherenceId());
        statement.bindLong(2, entity.getClientMedicationId());
        statement.bindLong(3, entity.getUserId());
        final String _tmp = __converters.fromLocalDateTimeToString(entity.getCheckedTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindDouble(5, entity.getAdherenceRate());
      }
    };
  }

  @Override
  public Object insertAdherenceLog(final AdherenceLog log,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAdherenceLog.insert(log);
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
      final Continuation<? super List<AdherenceLog>> $completion) {
    final String _sql = "SELECT * FROM adherence_logs WHERE clientMedicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cmId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AdherenceLog>>() {
      @Override
      @NonNull
      public List<AdherenceLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAdherenceId = CursorUtil.getColumnIndexOrThrow(_cursor, "adherenceId");
          final int _cursorIndexOfClientMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientMedicationId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCheckedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "checkedTime");
          final int _cursorIndexOfAdherenceRate = CursorUtil.getColumnIndexOrThrow(_cursor, "adherenceRate");
          final List<AdherenceLog> _result = new ArrayList<AdherenceLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AdherenceLog _item;
            final long _tmpAdherenceId;
            _tmpAdherenceId = _cursor.getLong(_cursorIndexOfAdherenceId);
            final long _tmpClientMedicationId;
            _tmpClientMedicationId = _cursor.getLong(_cursorIndexOfClientMedicationId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final LocalDateTime _tmpCheckedTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCheckedTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCheckedTime);
            }
            _tmpCheckedTime = __converters.fromStringToLocalDateTime(_tmp);
            final double _tmpAdherenceRate;
            _tmpAdherenceRate = _cursor.getDouble(_cursorIndexOfAdherenceRate);
            _item = new AdherenceLog(_tmpAdherenceId,_tmpClientMedicationId,_tmpUserId,_tmpCheckedTime,_tmpAdherenceRate);
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
