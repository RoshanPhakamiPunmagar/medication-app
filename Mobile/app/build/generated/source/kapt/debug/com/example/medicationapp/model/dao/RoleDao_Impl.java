package com.example.medicationapp.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.medicationapp.model.Role;
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
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RoleDao_Impl implements RoleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Role> __insertionAdapterOfRole;

  public RoleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRole = new EntityInsertionAdapter<Role>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `roles` (`roleId`,`roleName`) VALUES (nullif(?, 0),?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Role entity) {
        statement.bindLong(1, entity.getRoleId());
        if (entity.getRoleName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRoleName());
        }
      }
    };
  }

  @Override
  public Object insertRole(final Role role, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRole.insertAndReturnId(role);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllRoles(final Continuation<? super List<Role>> $completion) {
    final String _sql = "SELECT * FROM roles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Role>>() {
      @Override
      @NonNull
      public List<Role> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoleId = CursorUtil.getColumnIndexOrThrow(_cursor, "roleId");
          final int _cursorIndexOfRoleName = CursorUtil.getColumnIndexOrThrow(_cursor, "roleName");
          final List<Role> _result = new ArrayList<Role>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Role _item;
            final long _tmpRoleId;
            _tmpRoleId = _cursor.getLong(_cursorIndexOfRoleId);
            final String _tmpRoleName;
            if (_cursor.isNull(_cursorIndexOfRoleName)) {
              _tmpRoleName = null;
            } else {
              _tmpRoleName = _cursor.getString(_cursorIndexOfRoleName);
            }
            _item = new Role(_tmpRoleId,_tmpRoleName);
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
  public Object getRoleIdByName(final String roleName,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT roleId FROM roles WHERE roleName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (roleName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, roleName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
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
  public Object getAllRoleNames(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT roleName FROM roles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
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
  public Object getRoleById(final long id, final Continuation<? super Role> $completion) {
    final String _sql = "SELECT * FROM roles WHERE roleId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Role>() {
      @Override
      @Nullable
      public Role call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoleId = CursorUtil.getColumnIndexOrThrow(_cursor, "roleId");
          final int _cursorIndexOfRoleName = CursorUtil.getColumnIndexOrThrow(_cursor, "roleName");
          final Role _result;
          if (_cursor.moveToFirst()) {
            final long _tmpRoleId;
            _tmpRoleId = _cursor.getLong(_cursorIndexOfRoleId);
            final String _tmpRoleName;
            if (_cursor.isNull(_cursorIndexOfRoleName)) {
              _tmpRoleName = null;
            } else {
              _tmpRoleName = _cursor.getString(_cursorIndexOfRoleName);
            }
            _result = new Role(_tmpRoleId,_tmpRoleName);
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
