package com.example.madcampweek2.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var apiService: ApiService? = null

    fun getInstance(): ApiService {
        if (apiService == null) {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            apiService = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }
}