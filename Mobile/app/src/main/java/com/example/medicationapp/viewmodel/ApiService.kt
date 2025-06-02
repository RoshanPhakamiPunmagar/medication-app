package com.example.medicationapp.viewmodel

import com.example.medicationapp.model.AiAnalysisResponse
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.model.ClientWithMedicationsDTO
import com.example.medicationapp.model.LoginRequest
import com.example.medicationapp.model.Medication
import com.example.medicationapp.model.User
import com.example.medicationapp.model.dto.AdherenceLogDTO
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.model.dto.MedicationLogDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service interface defining endpoints for medication, client, user, and log management.
 *
 * This interface includes methods for:
 * - Retrieving medications assigned to clients for a specific carer.
 * - Fetching adherence logs and AI analysis data for patients.
 * - Posting medication logs.
 * - Paginated retrieval of clients.
 * - User authentication (login) and registration.
 * - Managing carers and clients, including assigning carers to clients and assigning medications.
 * - Fetching detailed medication descriptions, all medications, and all clients.
 * - Removing carers from clients.
 *
 * Each method corresponds to a specific REST API endpoint with appropriate HTTP verbs, paths, and parameters.
 */



interface ApiService {

    @GET("api/medication/get/{carerId}")
    fun getClientsMedicationOfLoggedUser(
        @Path("carerId") carerId: Long
    ): Call<List<ClientMedicationDTO>>


    @GET("mobile/logs/get/{id}")
    fun getAdherenceLogs(@Path("id") id: Long): Call<List<AdherenceLogDTO>>

    @GET("mobile/logs/get/ai/{patientId}")
    fun getAiAnalysis(@Path("patientId") patientId: Long): Call<AiAnalysisResponse>

    @POST("mobile/logs/post/log")
    suspend fun postMedicationLog(@Body dto: MedicationLogDTO): Response<Unit>

    @GET("api/clients")
    fun getClientsPaged(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Map<String, @JvmSuppressWildcards Any>>


    @POST("mobile/check")
    fun login(
        @Body request: LoginRequest
    ): Call<Map<String, @JvmSuppressWildcards Any>>

    @POST("mobile/user")
    fun register(
        @Body user: User
    ): Call<Map<String, String>>


    @GET("mobile/userCarer")
    fun getAllCarers(): Call<List<User>>

    @GET("mobile/meds/details")
    fun getMedicationWithName(@Query("medicationList") list: List<Long>): Call<ClientMedsDescriptions>

    @GET("api/medications")
    fun getAllMedications(): Call<List<Medication>>

    @GET("api/clients")
    fun getAllClients(): Call<List<Client>>

    @POST("api/medication/assign")
    fun assignMedication(@Body dto: ClientMedicationDTO): Call<ResponseBody>

    @POST("mobile/assignCarerToClient")
    @FormUrlEncoded
    fun assignCarerToClient(
        @Field("clientId") clientId: Long,
        @Field("carerUserId") carerUserId: Long
    ): Call<Map<String, String>>

    @GET("api/medication/clients-with-medications/{carerId}")
    fun getClientsWithMedications(
        @Path("carerId") carerId: Long
    ): Call<List<ClientWithMedicationsDTO>>

    @POST("mobile/removeCarerFromClient")
    @FormUrlEncoded
    fun removeCarerFromClient(
        @Field("clientId") clientId: Long
    ): Call<Map<String, String>>


}