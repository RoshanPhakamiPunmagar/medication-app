package com.example.medicationapp.viewmodel

import com.example.medicationapp.adapter.LocalDateAdapter
import com.example.medicationapp.adapter.LocalTimeAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime
/**
 * Singleton object that provides a configured Retrofit instance for network operations.
 *
 * Key Features:
 * - Uses a custom `GsonBuilder` to support serialization/deserialization of `LocalDate` and `LocalTime`
 *   via custom adapters (`LocalDateAdapter`, `LocalTimeAdapter`).
 * - Configured with the base URL for the deployed backend service.
 * - Exposes a globally accessible `retrofit` instance for use throughout the app.
 *
 * Notes:
 * - The base URL can be switched between local emulator and cloud deployment.
 */


object RetrofitService {

    private const val BASE_URL = "http://10.0.2.2:8080/" // For Android Emulator localhost

//    private const val BASE_URL = "https://medication-app-deployment.onrender.com/" // For Render Cloud

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
