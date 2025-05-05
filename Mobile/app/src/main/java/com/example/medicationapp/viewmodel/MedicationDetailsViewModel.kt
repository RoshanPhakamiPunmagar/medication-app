package com.example.medicationapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.medicationapp.ViewModel.ApiService
import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicationDetailsViewModel: ViewModel() {
    private val retrofitService = RetrofitService()
    private val apiService = retrofitService.getRetrofit().create(ApiService::class.java)

    var isLoading by mutableStateOf(false)
        private set

    var medsDetails by mutableStateOf<ClientMedsDescriptions?>(null)
        private set

    var error by mutableStateOf("")
        private set

    fun fetchMedicationDetails(medsName: List<String>) {
        isLoading = true
        error = ""
        println(medsName)
        apiService.getMedicationWithName(medsName).enqueue(object :
            Callback<ClientMedsDescriptions> {
            override fun onResponse(call: Call<ClientMedsDescriptions>, response: Response<ClientMedsDescriptions>) {
                isLoading = false
                if (response.isSuccessful) {
                    medsDetails = response.body()

                    println(medsDetails?.getInteractions())
                    println(medsDetails?.getRecommendations())
                } else {
                    error = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ClientMedsDescriptions>, t: Throwable) {
                isLoading = false
                error = "Failure: ${t.localizedMessage}"
            }
        })
    }


}