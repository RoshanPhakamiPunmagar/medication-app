package com.example.medicationapp.model.repository
import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.User
import com.example.medicationapp.repository.RoleRepository
import com.example.medicationapp.model.dao.UserDao

class UserRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val userDao: UserDao = db.userDao()
    private val roleRepository = RoleRepository(db.roleDao())

    suspend fun preloadRoles() {
        val existing = roleRepository.getAllRoles()
        if (existing.isEmpty()) {
            roleRepository.insertRole(1L, "Manager")
            roleRepository.insertRole(2L, "Carer")
        }
    }

    suspend fun registerUser(name: String, email: String, password: String, roleName: String): Result<Unit> {
        return roleRepository.getRoleIdByName(roleName)?.let { roleId ->
            val user = User(name = name, email = email, password = password, roleId = roleId)
            try {
                userDao.insertUser(user)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } ?: Result.failure(Exception("Invalid role selected."))
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun isUserExist(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }

    suspend fun getRoleNameById(roleId: Long): String? {
        return roleRepository.getRoleById(roleId)?.roleName
    }

    suspend fun getRoles(): List<String> {
        return roleRepository.getAllRoleNames()
    }
}
