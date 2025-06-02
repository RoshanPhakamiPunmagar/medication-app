package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationapp.model.*
import com.example.medicationapp.model.dto.ClientMedicationDTO
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import com.example.medicationapp.model.ClientMedication
class ClientMedicationViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitService.retrofit.create(ApiService::class.java)

    val assignStatus = MutableLiveData<Boolean>()
    val assignMessage = MutableLiveData<String>()
    val clientsWithMedications = MutableLiveData<List<ClientWithMedicationsDTO>>()
    val fetchErrorMessage = MutableLiveData<String>()
    // Added LiveData for medications and names
    val medications = MutableLiveData<List<ClientMedication>>()

    private var medsFetchJob: Job? = null
    private var clientsFetchJob: Job? = null


    private val _clientsMedsLoggedUser =  MutableStateFlow<List<ClientMedicationDTO>>(emptyList())
    val clientsMedsLoggedUser: StateFlow<List<ClientMedicationDTO>?> = _clientsMedsLoggedUser

    // Assign medication (already implemented)
    fun assignMedicationToClient(dto: ClientMedicationDTO) {
        apiService.assignMedication(dto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseText = response.body()?.string() ?: "Success"
                    assignStatus.postValue(true)
                    assignMessage.postValue(responseText)

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


    fun startFetchingMedsPeriodically(carerId: Long) {
        // Cancel any existing job to avoid multiple concurrent jobs
        medsFetchJob?.cancel()

        medsFetchJob = viewModelScope.launch {
            while (true) {
                try {

                    fetchClientMedsOfLoggedUser(carerId)
                    delay(30_000) // 30 seconds
                } catch (e: Exception) {
                    Log.e("PeriodicFetch", "Error fetching medications", e)
                    // Add delay before retry to avoid rapid retries on failure
                    delay(10_000)
                }
            }
        }
    }


    fun startFetchingClientWithMedsPeriodically(carerId: Long) {
        // Cancel any existing job to avoid multiple concurrent jobs
        clientsFetchJob?.cancel()

        clientsFetchJob = viewModelScope.launch {
            while (true) {
                try {

                    fetchClientsWithMedications(carerId)
                    delay(30_000) // 30 seconds
                } catch (e: Exception) {
                    Log.e("PeriodicFetch", "Error fetching clients", e)
                    // Add delay before retry to avoid rapid retries on failure
                    delay(10_000)
                }
            }
        }
    }


    fun stopAllPeriodicFetches() {
        medsFetchJob?.cancel()
        clientsFetchJob?.cancel()
        medsFetchJob = null
        clientsFetchJob = null
    }

    fun fetchClientMedsOfLoggedUser(carerId : Long){
        apiService.getClientsMedicationOfLoggedUser(carerId)
            .enqueue(object : Callback<List<ClientMedicationDTO>> {
                override fun onResponse(
                    call: Call<List<ClientMedicationDTO>>,
                    response: Response<List<ClientMedicationDTO>>
                ) {
                    if (response.isSuccessful) {
                        // Post the data into LiveData to update the UI
                        val clientMedsData = response.body()
                        if (clientMedsData != null) {

                            _clientsMedsLoggedUser.value = clientMedsData
                        } else {
                            // Handle case when response is empty
                            _clientsMedsLoggedUser.value = emptyList()
                        }
                    } else {
                        // Handle error if response is not successful
                        fetchErrorMessage.postValue(
                            "Error: ${response.errorBody()?.string() ?: response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<List<ClientMedicationDTO>>, t: Throwable) {
                    // Handle failure case
                    fetchErrorMessage.postValue("Failure: ${t.message}")
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

//    fun fetchMedications(medsId: Long, onResult: (String?) -> Unit) {
//        apiService.getMeds(medsId).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    onResult(response.body())
//                } else {
//                    onResult(null)
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                onResult(null)
//            }
//        })
//    }






}
