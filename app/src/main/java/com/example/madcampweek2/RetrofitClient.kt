package com.example.madcampweek2.network

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var apiService: ApiService? = null

    fun getInstance(context: Context): ApiService {
        if (apiService == null) {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            // Token Interceptor
            val tokenInterceptor = okhttp3.Interceptor { chain ->
                val originalRequest = chain.request()
                val sharedPrefs = context.getSharedPreferences("YourSharedPrefs", Context.MODE_PRIVATE)
                val token = sharedPrefs.getString("authToken", null)

                val newRequest = if (token != null) {
                    val requestWithToken = originalRequest.newBuilder()
                        .addHeader("Authorization", "Token $token")
                        .build()
                    Log.d("OkHttp", "Sending request with token: $token")
                    requestWithToken
                } else {
                    originalRequest
                }

                chain.proceed(newRequest)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("authToken", null)

                    if (token != null) {
                        val newRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", "Token $token")
                            .build()
                        chain.proceed(newRequest)
                    } else {
                        chain.proceed(originalRequest)
                    }
                }
                .build()

            apiService = Retrofit.Builder()
                .baseUrl("http://172.10.7.27:80/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }
}