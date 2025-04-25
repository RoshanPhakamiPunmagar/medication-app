package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.User
import com.example.medicationapp.model.repository.UserRepository
import com.example.medicationapp.repository.RoleRepository

class UserController(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val userRepository = UserRepository(db.userDao())
    private val roleRepository = RoleRepository(db.roleDao())

    suspend fun preloadRoles() {
        val existing = roleRepository.getAllRoles()
        if (existing.isEmpty()) {
            roleRepository.insertRole(1L, "Manager")
            roleRepository.insertRole(2L, "Carer")
        }
    }

    suspend fun registerUser(name: String, email: String, password: String, roleName: String) =
        roleRepository.getRoleIdByName(roleName)?.let { roleId ->
            val user = User(name = name, email = email, password = password, roleId = roleId)
            userRepository.register(user)
            Result.success(Unit)
        } ?: Result.failure(Exception("Invalid role selected."))

    suspend fun loginUser(email: String, password: String): User? =
        userRepository.login(email, password)

    suspend fun getRoleNameById(roleId: Long): String? =
        roleRepository.getRoleById(roleId)?.roleName

    suspend fun getRoles(): List<String> =
        roleRepository.getAllRoleNames()
}
