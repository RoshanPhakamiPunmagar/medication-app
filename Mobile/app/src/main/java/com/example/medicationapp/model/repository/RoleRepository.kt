package com.example.medicationapp.repository

import com.example.medicationapp.model.Role
import com.example.medicationapp.model.dao.RoleDao

class RoleRepository(private val roleDao: RoleDao) {
    suspend fun insertRole(roleId: Long, name: String): Long =
        roleDao.insertRole(Role(roleId = roleId, roleName = name))

    suspend fun getAllRoles() = roleDao.getAllRoles()

    suspend fun getRoleIdByName(roleName: String): Long? = // fixed
        roleDao.getRoleIdByName(roleName)

    suspend fun getAllRoleNames(): List<String> =
        roleDao.getAllRoleNames()

    suspend fun getRoleById(id: Long): Role? = // fixed
        roleDao.getRoleById(id)
}
