package com.example.medicationapp.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.medicationapp.model.Client;
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
public final class ClientDao_Impl implements ClientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Client> __insertionAdapterOfClient;

  private final EntityDeletionOrUpdateAdapter<Client> __deletionAdapterOfClient;

  private final EntityDeletionOrUpdateAdapter<Client> __updateAdapterOfClient;

  private final SharedSQLiteStatement __preparedStmtOfAssignClientToCarer;

  public ClientDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfClient = new EntityInsertionAdapter<Client>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `clients` (`clientId`,`name`,`dob`,`contactInfo`,`carerId`,`managerId`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Client entity) {
        statement.bindLong(1, entity.getClientId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDob() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDob());
        }
        if (entity.getContactInfo() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContactInfo());
        }
        if (entity.getCarerId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCarerId());
        }
        statement.bindLong(6, entity.getManagerId());
      }
    };
    this.__deletionAdapterOfClient = new EntityDeletionOrUpdateAdapter<Client>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `clients` WHERE `clientId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Client entity) {
        statement.bindLong(1, entity.getClientId());
      }
    };
    this.__updateAdapterOfClient = new EntityDeletionOrUpdateAdapter<Client>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `clients` SET `clientId` = ?,`name` = ?,`dob` = ?,`contactInfo` = ?,`carerId` = ?,`managerId` = ? WHERE `clientId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Client entity) {
        statement.bindLong(1, entity.getClientId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDob() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDob());
        }
        if (entity.getContactInfo() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContactInfo());
        }
        if (entity.getCarerId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCarerId());
        }
        statement.bindLong(6, entity.getManagerId());
        statement.bindLong(7, entity.getClientId());
      }
    };
    this.__preparedStmtOfAssignClientToCarer = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE clients SET carerId = ? WHERE clientId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertClient(final Client client, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfClient.insertAndReturnId(client);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteClient(final Client client, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfClient.handle(client);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateClient(final Client client, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfClient.handle(client);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object assignClientToCarer(final long clientId, final long carerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAssignClientToCarer.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, carerId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, clientId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfAssignClientToCarer.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllClients(final Continuation<? super List<Client>> $completion) {
    final String _sql = "SELECT * FROM clients";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Client>>() {
      @Override
      @NonNull
      public List<Client> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfContactInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "contactInfo");
          final int _cursorIndexOfCarerId = CursorUtil.getColumnIndexOrThrow(_cursor, "carerId");
          final int _cursorIndexOfManagerId = CursorUtil.getColumnIndexOrThrow(_cursor, "managerId");
          final List<Client> _result = new ArrayList<Client>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Client _item;
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDob;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmpDob = null;
            } else {
              _tmpDob = _cursor.getString(_cursorIndexOfDob);
            }
            final String _tmpContactInfo;
            if (_cursor.isNull(_cursorIndexOfContactInfo)) {
              _tmpContactInfo = null;
            } else {
              _tmpContactInfo = _cursor.getString(_cursorIndexOfContactInfo);
            }
            final Long _tmpCarerId;
            if (_cursor.isNull(_cursorIndexOfCarerId)) {
              _tmpCarerId = null;
            } else {
              _tmpCarerId = _cursor.getLong(_cursorIndexOfCarerId);
            }
            final long _tmpManagerId;
            _tmpManagerId = _cursor.getLong(_cursorIndexOfManagerId);
            _item = new Client(_tmpClientId,_tmpName,_tmpDob,_tmpContactInfo,_tmpCarerId,_tmpManagerId);
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
  public Object getClientsForCarer(final long carerId,
      final Continuation<? super List<Client>> $completion) {
    final String _sql = "SELECT * FROM clients WHERE carerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, carerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Client>>() {
      @Override
      @NonNull
      public List<Client> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfContactInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "contactInfo");
          final int _cursorIndexOfCarerId = CursorUtil.getColumnIndexOrThrow(_cursor, "carerId");
          final int _cursorIndexOfManagerId = CursorUtil.getColumnIndexOrThrow(_cursor, "managerId");
          final List<Client> _result = new ArrayList<Client>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Client _item;
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDob;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmpDob = null;
            } else {
              _tmpDob = _cursor.getString(_cursorIndexOfDob);
            }
            final String _tmpContactInfo;
            if (_cursor.isNull(_cursorIndexOfContactInfo)) {
              _tmpContactInfo = null;
            } else {
              _tmpContactInfo = _cursor.getString(_cursorIndexOfContactInfo);
            }
            final Long _tmpCarerId;
            if (_cursor.isNull(_cursorIndexOfCarerId)) {
              _tmpCarerId = null;
            } else {
              _tmpCarerId = _cursor.getLong(_cursorIndexOfCarerId);
            }
            final long _tmpManagerId;
            _tmpManagerId = _cursor.getLong(_cursorIndexOfManagerId);
            _item = new Client(_tmpClientId,_tmpName,_tmpDob,_tmpContactInfo,_tmpCarerId,_tmpManagerId);
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
  public Object getClientById(final long clientId, final Continuation<? super Client> $completion) {
    final String _sql = "SELECT * FROM clients WHERE clientId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clientId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Client>() {
      @Override
      @NonNull
      public Client call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfContactInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "contactInfo");
          final int _cursorIndexOfCarerId = CursorUtil.getColumnIndexOrThrow(_cursor, "carerId");
          final int _cursorIndexOfManagerId = CursorUtil.getColumnIndexOrThrow(_cursor, "managerId");
          final Client _result;
          if (_cursor.moveToFirst()) {
            final long _tmpClientId;
            _tmpClientId = _cursor.getLong(_cursorIndexOfClientId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDob;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmpDob = null;
            } else {
              _tmpDob = _cursor.getString(_cursorIndexOfDob);
            }
            final String _tmpContactInfo;
            if (_cursor.isNull(_cursorIndexOfContactInfo)) {
              _tmpContactInfo = null;
            } else {
              _tmpContactInfo = _cursor.getString(_cursorIndexOfContactInfo);
            }
            final Long _tmpCarerId;
            if (_cursor.isNull(_cursorIndexOfCarerId)) {
              _tmpCarerId = null;
            } else {
              _tmpCarerId = _cursor.getLong(_cursorIndexOfCarerId);
            }
            final long _tmpManagerId;
            _tmpManagerId = _cursor.getLong(_cursorIndexOfManagerId);
            _result = new Client(_tmpClientId,_tmpName,_tmpDob,_tmpContactInfo,_tmpCarerId,_tmpManagerId);
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
