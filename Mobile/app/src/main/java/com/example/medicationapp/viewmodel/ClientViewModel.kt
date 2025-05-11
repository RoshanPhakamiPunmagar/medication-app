package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientViewModel : ViewModel() {
    private val apiService: ApiService

    // LiveData to observe the list of clients
    val clientsLiveData = MutableLiveData<List<Client>>()

    init {
        // Initialize ApiService using RetrofitService
        apiService = RetrofitService.retrofit.create(ApiService::class.java)
    }

    fun getAllClients() {
        // Make the network call asynchronously
        apiService.getAllClients().enqueue(object : Callback<List<Client>> {
            override fun onResponse(call: Call<List<Client>>, response: Response<List<Client>>) {
                if (response.isSuccessful) {
                    // Update LiveData with the list of clients
                    clientsLiveData.postValue(response.body())
                    val clients = response.body()
                    Log.d("ClientViewModel", "Fetched clients: $clients")
                } else {
                    // Handle error (optional)
                    Log.e("ClientViewModel", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Client>>, t: Throwable) {
                // Handle failure (optional)
                Log.e("ClientViewModel", "Failure: ${t.message}")
            }
        })
    }
}
