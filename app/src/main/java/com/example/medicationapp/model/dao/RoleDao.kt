package com.example.medicationapp.model.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicationapp.model.Role

@Dao
interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: Role): Long

    @Query("SELECT * FROM roles")
    suspend fun getAllRoles(): List<Role>

    @Query("SELECT roleId FROM roles WHERE roleName = :roleName")
    suspend fun getRoleIdByName(roleName: String): Long?        // ← add this

    @Query("SELECT roleName FROM roles")
    suspend fun getAllRoleNames(): List<String>

    @Query("SELECT * FROM roles WHERE roleId = :id")
    suspend fun getRoleById(id: Long): Role?// ← and this
}
