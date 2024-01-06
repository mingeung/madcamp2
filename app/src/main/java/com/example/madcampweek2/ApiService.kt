package com.example.madcampweek2.network  // Replace with your actual package name

import com.example.madcampweek2.model.NewUser
import com.example.madcampweek2.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register/") // Replace with your actual registration endpoint
    fun registerUser(@Body newUser: NewUser): Call<UserResponse>
}