package com.example.madcampweek2.network

import android.content.Context
import android.util.JsonReader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
                level = HttpLoggingInterceptor.Level.BODY // 디버깅을 위해 추가
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            apiService = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8001/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(ApiService::class.java)
        }

        return apiService!!
    }
    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()
}