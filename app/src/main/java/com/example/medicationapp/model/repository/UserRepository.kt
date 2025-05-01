package com.example.medicationapp.model.repository

    import com.example.medicationapp.model.dao.UserDao
    import com.example.medicationapp.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun register(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? = userDao.login(email, password)

    suspend fun isUserExist(email: String): Boolean = userDao.getUserByEmail(email) != null
}
