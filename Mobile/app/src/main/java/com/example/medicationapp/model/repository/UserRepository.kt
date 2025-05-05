package com.example.medicationapp.model.repository

    import com.example.medicationapp.model.dao.UserDao
    import com.example.medicationapp.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun register(user: User): Result<Unit> {
        return try {
            userDao.insertUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User?> {
        return try {
            val user = userDao.login(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
