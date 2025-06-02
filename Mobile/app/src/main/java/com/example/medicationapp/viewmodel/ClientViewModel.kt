package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Client
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel responsible for managing paged retrieval of client data from the backend.
 *
 * Features:
 * - Fetches a paginated list of clients from the API with specified page number and page size.
 * - Parses and converts the raw response into a list of Client objects using Gson.
 * - Exposes LiveData for the clients list as well as pagination details (current page, total pages, total items).
 * - Handles API response success and failure, logging errors accordingly.
 * - Allows updating the current page number to support pagination controls in the UI.
 *
 * Utilizes Retrofit for network communication and Gson for JSON parsing.
 */


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
