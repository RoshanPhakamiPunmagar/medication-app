package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.LoginRequest
import com.example.medicationapp.model.Status
import com.example.medicationapp.model.User
import com.example.medicationapp.util.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel() : ViewModel() {

    private val apiService = RetrofitService.retrofit.create(ApiService::class.java)
    private lateinit var tokenManager: TokenManager

    fun setTokenManager(manager: TokenManager) {
        tokenManager = manager
    }


    private val _status = MutableStateFlow<Status?>(null)
    val status: StateFlow<Status?> = _status

    val carersLiveData = MutableLiveData<List<User>>()

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    val removeCarerStatus = MutableLiveData<Boolean>()
    val removeCarerMessage = MutableLiveData<String>()

    fun fetchCarers() {
        isLoading = true
        error = ""

        apiService.getAllCarers().enqueue(object : Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val allUsers = response.body() ?: emptyList()
                    allUsers.forEach {
                        println("User: ${it.name}, Role ID: ${it.roleId}")
                    }

                    val carers = allUsers.filter { it.roleId == 2L } // Filter carers
                    println("Fetched carers: ${carers.size}")
                    carers.forEach {
                        println("User: ${it.name}, Role ID: ${it.roleId}")
                    }
                    carersLiveData.postValue(carers)
                } else {
                    println("Response failed: ${response.code()} - ${response.message()}")
                }
            }



            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
            }
        })
    }

    /**
     * Attempts to log in a user using the provided email and password.
     * Sends a POST request to the backend API and handles the response asynchronously.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    fun login(email: String, password: String) {
        isLoading = true
        error = ""

        val loginRequest = LoginRequest(email, password)

        apiService.login(loginRequest).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                isLoading = false
                if (response.isSuccessful) {
                    val body = response.body()
                    val status = body?.get("status") as? String
                    val userId = (body?.get("userId") as? Double)?.toLong()
                    val roleId = (body?.get("roleId") as? Double)?.toLong()

                    _status.value = Status(status ?: "", userId, roleId)
                } else {
                    error = "Login failed: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.message}"
            }
        })
    }

    fun register(name: String, email: String, password: String) {
        isLoading = true
        error = ""

        val user = User(name = name, email = email, password = password, roleId = 2L)

        apiService.register(user).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                isLoading = false
                if (response.isSuccessful) {
                    when (val status = response.body()?.get("status")) {
                        "register" -> _status.value = Status("register")
                        "exists" -> error = "Email already exists"
                        else -> error = "Unknown signup response"
                    }
                } else {
                    error = "Signup failed: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
            }
        })
    }


    fun assignCarerToClient(clientId: Long, carerUserId: Long) {
        isLoading = true
        error = ""

        apiService.assignCarerToClient(clientId, carerUserId)
            .enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    isLoading = false
                    if (response.isSuccessful) {
                        val message = response.body()?.get("message") ?: "Assignment successful"
                        println("Server message: $message")
                    } else {
                        error = "Error assigning carer: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    isLoading = false
                    error = "Network error: ${t.localizedMessage}"
                }
            })
    }

    fun removeCarerFromClient(clientId: Long) {
        apiService.removeCarerFromClient(clientId).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(
                call: Call<Map<String, String>>,
                response: Response<Map<String, String>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.get("status") == "carerRemoved") {
                        removeCarerStatus.postValue(true)
                        removeCarerMessage.postValue("Carer removed successfully.")
                    } else {
                        removeCarerStatus.postValue(false)
                        removeCarerMessage.postValue("Failed to remove carer.")
                    }
                } else {
                    removeCarerStatus.postValue(false)
                    removeCarerMessage.postValue("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                removeCarerStatus.postValue(false)
                removeCarerMessage.postValue("Failure: ${t.message}")
            }
        })
    }

}
