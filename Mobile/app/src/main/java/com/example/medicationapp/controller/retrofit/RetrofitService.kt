package com.example.medicationapp.retrofit

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/" // Use 10.0.2.2 for localhost on emulator

        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }
}

