package com.example.medicationapp.viewmodel

import com.example.medicationapp.model.*
import com.example.medicationapp.model.dto.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/medication/get/{carerId}")
    fun getClientsMedicationOfLoggedUser(
        @Path("carerId") carerId: Long
    ): Call<List<ClientMedicationDTO>>

    @GET("logs/get/{id}")
    fun getAdherenceLogs(@Path("id") id: Long): Call<List<AdherenceLogDTO>>

    @GET("logs/get/ai/{patientId}")
    fun getAiAnalysis(@Path("patientId") patientId: Long): Call<AiAnalysisResponse>

    @POST("logs/post/log")
    suspend fun postMedicationLog(@Body dto: MedicationLogDTO): Response<Unit>

    @GET("api/clients")
    fun getClientsPaged(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Map<String, @JvmSuppressWildcards Any>>

    @POST("mobile/check")
    fun login(@Body request: LoginRequest): Call<Map<String, @JvmSuppressWildcards Any>>

    @POST("mobile/user")
    fun register(@Body user: User): Call<Map<String, String>>

    @GET("mobile/userCarer")
    fun getAllCarers(): Call<List<User>>

    @GET("meds/details")
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
