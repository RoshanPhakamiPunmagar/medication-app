package com.example.medicationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.Medication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel responsible for handling medication data.
 *
 * Responsibilities:
 * - Fetches the list of all medications from the backend using Retrofit.
 * - Exposes LiveData for medications and error messages to be observed by the UI.
 * - Handles both success and failure responses from the network call.
 * - Maintains clean separation between data handling and UI logic.
 */

class MedicationViewModel : ViewModel() {

    private val _medications = MutableLiveData<List<Medication>>()
    val medications: LiveData<List<Medication>> = _medications

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val api = RetrofitService.retrofit.create(ApiService::class.java)

    fun fetchMedications() {
        api.getAllMedications().enqueue(object : Callback<List<Medication>> {
            override fun onResponse(
                call: Call<List<Medication>>,
                response: Response<List<Medication>>
            ) {
                if (response.isSuccessful) {
                    _medications.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Medication>>, t: Throwable) {
                _error.postValue("Failure: ${t.message}")
            }
        })
    }
}
