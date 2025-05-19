package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Client
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientViewModel : ViewModel() {
    private val apiService = RetrofitService.retrofit.create(ApiService::class.java)

    val clientsLiveData = MutableLiveData<List<Client>>()
    val currentPage = MutableLiveData<Int>()
    val totalPages = MutableLiveData<Int>()
    val totalItems = MutableLiveData<Long>()

    fun setCurrentPage(page: Int) {
        currentPage.postValue(page)
    }


    fun getClientsPaged(page: Int, size: Int) {
        apiService.getClientsPaged(page, size).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(
                call: Call<Map<String, Any>>,
                response: Response<Map<String, Any>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { responseMap ->
                        val clientsJson = responseMap["clients"]
                        val gson = Gson()
                        val jsonStr = gson.toJson(clientsJson)
                        val newClients = gson.fromJson(jsonStr, Array<Client>::class.java).toList()

                        clientsLiveData.postValue(newClients)

                        // Update pagination info
                        currentPage.postValue((responseMap["currentPage"] as Double).toInt())
                        totalPages.postValue((responseMap["totalPages"] as Double).toInt())
                        totalItems.postValue((responseMap["totalItems"] as Double).toLong())
                    }
                } else {
                    Log.e("ClientViewModel", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Log.e("ClientViewModel", "Failure: ${t.message}")
            }
        })
    }




}
