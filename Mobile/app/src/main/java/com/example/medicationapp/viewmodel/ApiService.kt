<<<<<<<< HEAD:Mobile/app/src/main/java/com/example/medicationapp/viewmodel/ApiService.kt
package com.example.medicationapp.ViewModel

import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.model.Status
========
package com.example.medicationapp.controller.ViewModel

import com.example.medicationapp.model.ClientMedsDescriptions
>>>>>>>> main:Mobile/app/src/main/java/com/example/medicationapp/controller/ViewModel/ApiService.kt
import com.example.medicationapp.model.User
import retrofit2.Call
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

    @GET("meds/details")
    fun getMedicationWithName(@Query("medicationList") list: List<String>): Call<ClientMedsDescriptions>


}