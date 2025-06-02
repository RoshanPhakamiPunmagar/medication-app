package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.ClientMedsDescriptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel responsible for fetching and managing detailed medication information.
 *
 * Responsibilities:
 * - Retrieves medication details from the backend based on a list of medication IDs.
 * - Maintains UI state for loading status, fetched medication details, and error messages using Compose's mutableStateOf.
 * - Handles API responses asynchronously via Retrofit, updating state accordingly.
 * - Exposes state variables to allow UI components to reactively display loading, data, or error states.
 */

class MedicationDetailsViewModel : ViewModel() {

    // Use the singleton RetrofitService object
    private val apiService = RetrofitService.retrofit.create(ApiService::class.java)

    var isLoading by mutableStateOf(false)
        private set

    var medsDetails by mutableStateOf<ClientMedsDescriptions?>(null)
        private set

    var error by mutableStateOf("")
        private set

    fun fetchMedicationDetails(medsName: List<Long>) {
        isLoading = true
        error = ""

        apiService.getMedicationWithName(medsName).enqueue(object : Callback<ClientMedsDescriptions> {
            override fun onResponse(call: Call<ClientMedsDescriptions>, response: Response<ClientMedsDescriptions>) {

                isLoading = false
                if (response.isSuccessful) {
                    medsDetails = response.body()

                } else {
                    error = "Error: Try again"
                }
            }

            override fun onFailure(call: Call<ClientMedsDescriptions>, t: Throwable) {
                isLoading = false
                error = "Failure: ${t.localizedMessage}"
            }
        })
    }
}
