package com.example.medicationapp.controller.rest

import com.example.medicationapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("meds/{name}")
    fun getMedicationWithName(@Path("name") name: String): Call<User>


}