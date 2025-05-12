package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class ClientMedicationViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitService.retrofit.create(ApiService::class.java)

    val assignStatus = MutableLiveData<Boolean>()
    val assignMessage = MutableLiveData<String>()
    val clientsWithMedications = MutableLiveData<List<ClientWithMedicationsDTO>>()
    val fetchErrorMessage = MutableLiveData<String>()
    // Added LiveData for medications and names
    val medications = MutableLiveData<List<ClientMedication>>()




    // Assign medication (already implemented)
    fun assignMedicationToClient(dto: ClientMedication) {
        apiService.assignMedication(dto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseText = response.body()?.string() ?: "Success"
                    assignStatus.postValue(true)
                    assignMessage.postValue(responseText)
                    Log.d("AssignMedication", "Success: $responseText")
                } else {
                    val errorText = response.errorBody()?.string() ?: "Unknown error"
                    assignStatus.postValue(false)
                    assignMessage.postValue("Error: $errorText")
                    Log.e("AssignMedication", "Error: $errorText")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                assignStatus.postValue(false)
                assignMessage.postValue("Failure: ${t.message}")
                Log.e("AssignMedication", "Failure: ${t.message}", t)
            }
        })


    }

    fun fetchClientsWithMedications(carerId: Long) {
        apiService.getClientsWithMedications(carerId)
            .enqueue(object : Callback<List<ClientWithMedicationsDTO>> {
                override fun onResponse(
                    call: Call<List<ClientWithMedicationsDTO>>,
                    response: Response<List<ClientWithMedicationsDTO>>
                ) {
                    if (response.isSuccessful) {
                        // Post the data into LiveData to update the UI
                        val clientsData = response.body()
                        if (clientsData != null) {
                            // Filter out clients who have medications assigned to them
                            val filteredData = clientsData.filter { it.medications.isNotEmpty() }
                            clientsWithMedications.postValue(filteredData)
                        } else {
                            // Handle case when response is empty
                            clientsWithMedications.postValue(emptyList())
                        }
                    } else {
                        // Handle error if response is not successful
                        fetchErrorMessage.postValue(
                            "Error: ${response.errorBody()?.string() ?: response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<List<ClientWithMedicationsDTO>>, t: Throwable) {
                    // Handle failure case
                    fetchErrorMessage.postValue("Failure: ${t.message}")
                }
            })
    }




}
