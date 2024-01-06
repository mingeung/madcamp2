package com.example.madcampweek2.network  // Replace with your actual package name

import com.example.madcampweek2.model.NewUser
import com.example.madcampweek2.model.ProfileData
import com.example.madcampweek2.model.UserProfileResponse
import com.example.madcampweek2.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register/")
    fun registerUser(@Body newUser: NewUser): Call<UserResponse>

    @POST("update_profile/")
    fun updateProfile(@Body profileData: ProfileData): Call<UserResponse>

    @GET("profile/") // Updated with the actual endpoint
    fun getUserProfile(): Call<UserProfileResponse>
}