//ApiService.kt
package com.example.madcampweek2.network  // Replace with your actual package name

import CommentService
import com.example.madcampweek2.model.LoginResponse
import com.example.madcampweek2.model.NewUser
import com.example.madcampweek2.model.ProfileData
import com.example.madcampweek2.model.UserCheckResponse
import com.example.madcampweek2.model.UserCredentials
import com.example.madcampweek2.model.UserProfileResponse
import com.example.madcampweek2.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @POST("register/")
    fun registerUser(@Body newUser: NewUser): Call<UserResponse>

    @POST("update_profile/")
    fun updateProfile(@Body profileData: ProfileData): Call<UserResponse>

    @GET("profile/") // Updated with the actual endpoint
    fun getUserProfile(): Call<UserProfileResponse>

    @POST("login/")
    fun loginUser(@Body credentials: UserCredentials): Call<LoginResponse>

    @POST("logout/")
    fun logoutUser(): Call<UserResponse>

    @GET("checkUser/")
    fun checkUser(@Query("email") email: String): Call<UserCheckResponse>
    fun create(java: Class<CommentService>): Any
//    abstract fun create(java: Class<CommentService>): Any
}