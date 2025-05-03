package com.example.medicationapp.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.medicationapp.model.Medication;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class MedicationDao_Impl implements MedicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Medication> __insertionAdapterOfMedication;

  private final EntityDeletionOrUpdateAdapter<Medication> __deletionAdapterOfMedication;

  private final EntityDeletionOrUpdateAdapter<Medication> __updateAdapterOfMedication;

  public MedicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedication = new EntityInsertionAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medications` (`medicationId`,`name`,`description`,`sideEffects`,`interactionInfo`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getMedicationId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSideEffects() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSideEffects());
        }
        if (entity.getInteractionInfo() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getInteractionInfo());
        }
      }
    };
    this.__deletionAdapterOfMedication = new EntityDeletionOrUpdateAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `medications` WHERE `medicationId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getMedicationId());
      }
    };
    this.__updateAdapterOfMedication = new EntityDeletionOrUpdateAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medications` SET `medicationId` = ?,`name` = ?,`description` = ?,`sideEffects` = ?,`interactionInfo` = ? WHERE `medicationId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getMedicationId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSideEffects() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSideEffects());
        }
        if (entity.getInteractionInfo() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getInteractionInfo());
        }
        statement.bindLong(6, entity.getMedicationId());
      }
    };
  }

  @Override
  public Object insertMedication(final Medication medication,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMedication.insertAndReturnId(medication);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMedication(final Medication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMedication(final Medication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllMedications(final Continuation<? super List<Medication>> $completion) {
    final String _sql = "SELECT * FROM medications";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSideEffects = CursorUtil.getColumnIndexOrThrow(_cursor, "sideEffects");
          final int _cursorIndexOfInteractionInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionInfo");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final int _tmpMedicationId;
            _tmpMedicationId = _cursor.getInt(_cursorIndexOfMedicationId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSideEffects;
            if (_cursor.isNull(_cursorIndexOfSideEffects)) {
              _tmpSideEffects = null;
            } else {
              _tmpSideEffects = _cursor.getString(_cursorIndexOfSideEffects);
            }
            final String _tmpInteractionInfo;
            if (_cursor.isNull(_cursorIndexOfInteractionInfo)) {
              _tmpInteractionInfo = null;
            } else {
              _tmpInteractionInfo = _cursor.getString(_cursorIndexOfInteractionInfo);
            }
            _item = new Medication(_tmpMedicationId,_tmpName,_tmpDescription,_tmpSideEffects,_tmpInteractionInfo);
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
  public Object getMedicationById(final long id,
      final Continuation<? super Medication> $completion) {
    final String _sql = "SELECT * FROM medications WHERE medicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Medication>() {
      @Override
      @Nullable
      public Medication call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSideEffects = CursorUtil.getColumnIndexOrThrow(_cursor, "sideEffects");
          final int _cursorIndexOfInteractionInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionInfo");
          final Medication _result;
          if (_cursor.moveToFirst()) {
            final int _tmpMedicationId;
            _tmpMedicationId = _cursor.getInt(_cursorIndexOfMedicationId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSideEffects;
            if (_cursor.isNull(_cursorIndexOfSideEffects)) {
              _tmpSideEffects = null;
            } else {
              _tmpSideEffects = _cursor.getString(_cursorIndexOfSideEffects);
            }
            final String _tmpInteractionInfo;
            if (_cursor.isNull(_cursorIndexOfInteractionInfo)) {
              _tmpInteractionInfo = null;
            } else {
              _tmpInteractionInfo = _cursor.getString(_cursorIndexOfInteractionInfo);
            }
            _result = new Medication(_tmpMedicationId,_tmpName,_tmpDescription,_tmpSideEffects,_tmpInteractionInfo);
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
