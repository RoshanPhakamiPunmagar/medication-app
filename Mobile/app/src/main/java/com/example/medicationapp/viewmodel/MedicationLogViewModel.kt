package com.example.medicationapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.R
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.medicationapp.model.AiAnalysisResponse
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.dto.AdherenceLogDTO
import com.example.medicationapp.model.dto.MedicationLogDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.jvm.java


class MedicationLogViewModel : ViewModel() {



    private val _logs  = MutableStateFlow<List<AdherenceLogDTO>>(emptyList())
    val adhlogs: StateFlow<List<AdherenceLogDTO>?> = _logs


    var success by mutableStateOf(false)
    private val api = RetrofitService.retrofit.create(ApiService::class.java)


    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    private val _aiAnalysis = MutableStateFlow<AiAnalysisResponse?>(null)
    val aiAnalysis: StateFlow<AiAnalysisResponse?> = _aiAnalysis

    fun postLog(log: MedicationLogDTO) {
        viewModelScope.launch {
            isLoading = true
            error = ""
            success = false

            try {
                val response = api.postMedicationLog(log)
                if (response.isSuccessful) {
                    success = true
                } else {
                    error = "Server error: ${response.code()}"
                }
            } catch (e: Exception) {
                error = "Network error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchLogs(id: Long) {
        isLoading = true
        error = ""
        api.getAdherenceLogs(id).enqueue(object: Callback<List<AdherenceLogDTO>>
        {

            override fun onResponse(
                call: Call<List<AdherenceLogDTO>>,
                response: Response<List<AdherenceLogDTO>>
            ) {
                isLoading = false
                if(response.isSuccessful)
                {
                    _logs.value = response.body()!!

                }
            }

            override fun onFailure(
                call: Call<List<AdherenceLogDTO>?>,
                t: Throwable
            ) {
                isLoading = false
                error = "Failure: ${t.localizedMessage}"
            }

        }
        )
    }

    fun fetchAiLogs(id: Long) {
        isLoading = true
        error = ""
        api.getAiAnalysis(id).enqueue(object : Callback<AiAnalysisResponse> {
            override fun onResponse(
                call: Call<AiAnalysisResponse>,
                response: Response<AiAnalysisResponse>
            ) {
                isLoading = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _aiAnalysis.value = it
                    } ?: run {
                        error = "Empty response"
                    }
                } else {
                    error = "Server error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<AiAnalysisResponse>, t: Throwable) {
                isLoading = false
                error = "Network error: ${t.localizedMessage}"
                Log.e("FetchAI", "Network error", t)
            }
        })
    }




}
