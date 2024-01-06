package com.example.madcampweek2.network  // Replace with your actual package name

import com.example.madcampweek2.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitInstance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}