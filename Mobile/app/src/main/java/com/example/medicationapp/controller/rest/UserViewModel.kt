package com.example.medicationapp.controller.rest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Status
import com.example.medicationapp.model.User
import com.example.medicationapp.retrofit.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val retrofitService = RetrofitService()
    private val apiService = retrofitService.getRetrofit().create(ApiService::class.java)


    private val _status = MutableStateFlow<Status?>(null)
    val status: StateFlow<Status?> = _status


    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set


    fun login(email: String, password: String) {
        isLoading = true
        error = ""

        apiService.login(email, password).enqueue(object : Callback<Status> {
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                isLoading = false
                if (response.isSuccessful && response.body() != null) {
                    _status.value = response.body()
                } else {
                    error = "Login failed: ${response.code()}"
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

        apiService.register(user).enqueue(object : Callback<Status> {
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                isLoading = false

                if (response.isSuccessful) {
                    _status.value = response.body()
                   // Log.e("APIs",  _status.value ?.fetchStatus().toString())
                } else {
                    error = when (response.code()) {
                        401 -> "Invalid credentials"
                        404 -> "User not found"
                        else -> "Login failed: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
                Log.e("API", "Login failed", t)
            }
        })
    }
}