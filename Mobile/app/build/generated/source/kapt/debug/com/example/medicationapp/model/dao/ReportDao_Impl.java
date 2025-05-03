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
import com.example.medicationapp.model.Report;
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
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Report> __insertionAdapterOfReport;

  private final Converters __converters = new Converters();

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReport = new EntityInsertionAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`reportId`,`carerId`,`clientId`,`notes`,`dateCreated`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindLong(1, entity.getReportId());
        statement.bindLong(2, entity.getCarerId());
        statement.bindLong(3, entity.getClientId());
        if (entity.getNotes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNotes());
        }
        final String _tmp = __converters.fromLocalDateTimeToString(entity.getDateCreated());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
      }
    };
  }

  @Override
  public Object insertReport(final Report report, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReport.insert(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getReportsByManager(final int carerId,
      final Continuation<? super List<Report>> $completion) {
    final String _sql = "SELECT * FROM reports WHERE carerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, carerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Report>>() {
      @Override
      @NonNull
      public List<Report> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportId");
          final int _cursorIndexOfCarerId = CursorUtil.getColumnIndexOrThrow(_cursor, "carerId");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDateCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "dateCreated");
          final List<Report> _result = new ArrayList<Report>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Report _item;
            final long _tmpReportId;
            _tmpReportId = _cursor.getLong(_cursorIndexOfReportId);
            final int _tmpCarerId;
            _tmpCarerId = _cursor.getInt(_cursorIndexOfCarerId);
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final LocalDateTime _tmpDateCreated;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDateCreated)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDateCreated);
            }
            _tmpDateCreated = __converters.fromStringToLocalDateTime(_tmp);
            _item = new Report(_tmpReportId,_tmpCarerId,_tmpClientId,_tmpNotes,_tmpDateCreated);
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
