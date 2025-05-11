package com.example.medicationapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicationapp.model.ClientMedication
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientMedicationViewModel : ViewModel() {

    private val apiService = RetrofitService.retrofit.create(ApiService::class.java)

    val assignStatus = MutableLiveData<Boolean>()
    val assignMessage = MutableLiveData<String>()

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
}
