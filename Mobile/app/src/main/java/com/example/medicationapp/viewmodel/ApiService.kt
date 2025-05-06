package com.example.medicationapp.viewmodel

import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.model.Status

import com.example.medicationapp.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

        @POST("mobile/check")
        @FormUrlEncoded
        fun login(
            @Field("email") email: String,
            @Field("password") password: String
        ): Call<Status>

    @POST("mobile/user")
    fun register(
        @Body user: User,
    ): Call<Status>

    @GET("meds/details")
    fun getMedicationWithName(@Query("medicationList") list: List<String>): Call<ClientMedsDescriptions>


}