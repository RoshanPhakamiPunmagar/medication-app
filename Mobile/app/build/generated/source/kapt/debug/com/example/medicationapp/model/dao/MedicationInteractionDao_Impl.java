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
import com.example.medicationapp.model.MedicationInteraction;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MedicationInteractionDao_Impl implements MedicationInteractionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicationInteraction> __insertionAdapterOfMedicationInteraction;

  public MedicationInteractionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicationInteraction = new EntityInsertionAdapter<MedicationInteraction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medication_interactions` (`interactionId`,`medication_id_1`,`medication_id_2`,`interactionDescription`,`severity`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationInteraction entity) {
        statement.bindLong(1, entity.getInteractionId());
        statement.bindLong(2, entity.getMedication1Id());
        statement.bindLong(3, entity.getMedication2Id());
        if (entity.getInteractionDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getInteractionDescription());
        }
        statement.bindString(5, __Severity_enumToString(entity.getSeverity()));
      }
    };
  }

  @Override
  public Object insertInteraction(final MedicationInteraction interaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicationInteraction.insert(interaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getInteractionsForMedication(final int id,
      final Continuation<? super List<MedicationInteraction>> $completion) {
    final String _sql = "SELECT * FROM medication_interactions WHERE medication_id_1 = ? OR medication_id_2 = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    _argIndex = 2;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MedicationInteraction>>() {
      @Override
      @NonNull
      public List<MedicationInteraction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInteractionId = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionId");
          final int _cursorIndexOfMedication1Id = CursorUtil.getColumnIndexOrThrow(_cursor, "medication_id_1");
          final int _cursorIndexOfMedication2Id = CursorUtil.getColumnIndexOrThrow(_cursor, "medication_id_2");
          final int _cursorIndexOfInteractionDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionDescription");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final List<MedicationInteraction> _result = new ArrayList<MedicationInteraction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationInteraction _item;
            final long _tmpInteractionId;
            _tmpInteractionId = _cursor.getLong(_cursorIndexOfInteractionId);
            final long _tmpMedication1Id;
            _tmpMedication1Id = _cursor.getLong(_cursorIndexOfMedication1Id);
            final long _tmpMedication2Id;
            _tmpMedication2Id = _cursor.getLong(_cursorIndexOfMedication2Id);
            final String _tmpInteractionDescription;
            if (_cursor.isNull(_cursorIndexOfInteractionDescription)) {
              _tmpInteractionDescription = null;
            } else {
              _tmpInteractionDescription = _cursor.getString(_cursorIndexOfInteractionDescription);
            }
            final MedicationInteraction.Severity _tmpSeverity;
            _tmpSeverity = __Severity_stringToEnum(_cursor.getString(_cursorIndexOfSeverity));
            _item = new MedicationInteraction(_tmpInteractionId,_tmpMedication1Id,_tmpMedication2Id,_tmpInteractionDescription,_tmpSeverity);
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

  private String __Severity_enumToString(@NonNull final MedicationInteraction.Severity _value) {
    switch (_value) {
      case LOW: return "LOW";
      case MEDIUM: return "MEDIUM";
      case HIGH: return "HIGH";
      case CRITICAL: return "CRITICAL";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private MedicationInteraction.Severity __Severity_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "LOW": return MedicationInteraction.Severity.LOW;
      case "MEDIUM": return MedicationInteraction.Severity.MEDIUM;
      case "HIGH": return MedicationInteraction.Severity.HIGH;
      case "CRITICAL": return MedicationInteraction.Severity.CRITICAL;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
