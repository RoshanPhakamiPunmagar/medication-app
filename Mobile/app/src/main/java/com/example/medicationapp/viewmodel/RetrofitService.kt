package com.example.medicationapp.viewmodel

import com.example.medicationapp.adapter.LocalDateAdapter
import com.example.medicationapp.adapter.LocalTimeAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime

object RetrofitService {

    private const val BASE_URL = "http://10.0.2.2:8080/" // For Android Emulator localhost

    // Custom Gson to handle LocalDate and LocalTime
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .create()

    // Retrofit instance with custom Gson converter
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
