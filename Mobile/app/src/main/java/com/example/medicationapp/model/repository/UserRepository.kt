package com.example.medicationapp.model.repository

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.User
import com.example.medicationapp.repository.RoleRepository
import com.example.medicationapp.model.dao.UserDao

/**
 * Repository class for handling user-related operations.
 * Acts as an intermediary between the UI/ViewModel layer and the DAO/database layer.
 */
class UserRepository(context: Context) {

    // Initialize the Room database
    private val db = AppDatabase.getDatabase(context)

    // Get the User DAO to perform user-specific database operations
    private val userDao: UserDao = db.userDao()

    // Create an instance of RoleRepository to manage roles
    private val roleRepository = RoleRepository(db.roleDao())

    /**
     * Registers a new user in the system.
     * - Finds role ID by name (e.g., "Manager", "Carer").
     * - Creates a User object and inserts it using the DAO.
     * - Returns success or failure result.
     */
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        roleName: String
    ): Result<Unit> {
        return roleRepository.getRoleIdByName(roleName)?.let { roleId ->
            val user = User(name = name, email = email, password = password, roleId = roleId)
            try {
                userDao.insertUser(user)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e) // Return failure if insertion fails
            }
        }
            ?: Result.failure(Exception("Invalid role selected.")) // Return failure if role name not found
    }

    /**
     * Logs in a user by checking if the email and password match a record in the database.
     * Returns the User object if valid, or null if login fails.
     */
    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    /**
     * Checks if a user already exists with the given email.
     * Returns true if found, false otherwise.
     */
    suspend fun isUserExist(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }

    /**
     * Gets the role name (e.g., "Manager", "Carer") for a given role ID.
     */
    suspend fun getRoleNameById(roleId: Long): String? {
        return roleRepository.getRoleById(roleId)?.roleName
    }

    /**
     * Returns a list of all role names available in the system.
     * Useful for dropdowns in registration forms.
     */
    suspend fun getRoles(): List<String> {
        return roleRepository.getAllRoleNames()
    }
}