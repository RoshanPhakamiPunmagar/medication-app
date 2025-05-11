package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Status
import com.example.medicationapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val apiService = RetrofitService.retrofit.create(ApiService::class.java)

    private val _status = MutableStateFlow<Status?>(null)
    val status: StateFlow<Status?> = _status

    val carersLiveData = MutableLiveData<List<User>>()

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    fun fetchCarers() {
        isLoading = true
        error = ""

        apiService.getAllCarers().enqueue(object : Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                // Check if the HTTP response was successful
                if (response.isSuccessful) {
                    // Safely extract the list of users from the response body
                    val users = response.body() ?: emptyList()
                    println("Fetched users: ${users.size}")

                    // Log each user's name and role ID for debugging
                    users.forEach {
                        println("User: ${it.name}, Role ID: ${it.roleId}")
                    }

                    // Update LiveData with the fetched list of users to notify observers
                    carersLiveData.postValue(users)
                } else {
                    // Log error details if the response was not successful
                    println("Response failed: ${response.code()} - ${response.message()}")
                }
            }


            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
            }
        })
    }

    fun login(email: String, password: String) {
        isLoading = true
        error = ""

        apiService.login(email, password).enqueue(object : Callback<Status> {
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                isLoading = false
                if (response.isSuccessful) {
                    _status.value = response.body()
                } else {
                    error = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
            }
        })
    }

    fun register(user: User) {
        isLoading = true
        error = ""
        Log.d("called,", "Called")

        apiService.register(user).enqueue(object : Callback<Status> {
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                isLoading = false
                if (response.isSuccessful) {
                    _status.value = response.body()
                    Log.e("APIs", _status.value?.getStatus().toString())
                } else {
                    error = when (response.code()) {
                        401 -> "Invalid credentials"
                        404 -> "User not found"
                        else -> "Registration failed: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
                Log.e("API", "Registration failed", t)
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

}
