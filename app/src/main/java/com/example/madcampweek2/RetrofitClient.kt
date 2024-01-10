package com.example.madcampweek2.network

import CommentService
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var apiService: ApiService? = null
    private var commentService: CommentService? = null

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getInstance(context: Context): ApiService {
        if (apiService == null) {
            // OkHttpClient 초기화
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
                .baseUrl("http://10.0.2.2:8001/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }

    // 댓글 가져오기
    fun getCommentService(context: Context): CommentService {
        if (commentService == null) {
            // OkHttpClient 초기화
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

            commentService = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8001/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(CommentService::class.java)
        }
        return commentService!!
    }
}
