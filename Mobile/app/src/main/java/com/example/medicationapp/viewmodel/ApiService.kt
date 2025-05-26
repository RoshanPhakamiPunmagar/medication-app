package com.example.medicationapp.viewmodel

import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.model.ClientWithMedicationsDTO
import com.example.medicationapp.model.LoginRequest
import com.example.medicationapp.model.Medication
import com.example.medicationapp.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("https://medication-app-deployment.onrender.com/api/clients")
    fun getClientsPaged(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Map<String, @JvmSuppressWildcards Any>>



    @POST("https://medication-app-deployment.onrender.com/mobile/check")
    fun login(
        @Body request: LoginRequest
    ): Call<Map<String, @JvmSuppressWildcards Any>>

    @POST("https://medication-app-deployment.onrender.com/mobile/user")
    fun register(
        @Body user: User
    ): Call<Map<String, String>>


    @GET("https://medication-app-deployment.onrender.com/mobile/userCarer")
    fun getAllCarers(): Call<List<User>>

    @GET("https://medication-app-deployment.onrender.com/meds/details")
    fun getMedicationWithName(@Query("medicationList") list: List<String>): Call<ClientMedsDescriptions>

    @GET("https://medication-app-deployment.onrender.com/api/medications")
    fun getAllMedications(): Call<List<Medication>>

    @GET("https://medication-app-deployment.onrender.com/api/clients")
    fun getAllClients(): Call<List<Client>>

    @POST("https://medication-app-deployment.onrender.com/api/medication/assign")
    fun assignMedication(@Body dto: ClientMedication): Call<ResponseBody>

    @POST("https://medication-app-deployment.onrender.com/mobile/assignCarerToClient")
    @FormUrlEncoded
    fun assignCarerToClient(
        @Field("clientId") clientId: Long,
        @Field("carerUserId") carerUserId: Long
    ): Call<Map<String, String>>

    @GET("https://medication-app-deployment.onrender.com/api/medication/clients-with-medications/{carerId}")
    fun getClientsWithMedications(
        @Path("carerId") carerId: Long
    ): Call<List<ClientWithMedicationsDTO>>

    @POST("https://medication-app-deployment.onrender.com/mobile/removeCarerFromClient")
    @FormUrlEncoded
    fun removeCarerFromClient(
        @Field("clientId") clientId: Long
    ): Call<Map<String, String>>


}