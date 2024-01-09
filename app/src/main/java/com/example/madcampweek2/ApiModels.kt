package com.example.madcampweek2.model

import com.google.gson.annotations.SerializedName

// Model for creating a new user
data class NewUser(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    @SerializedName("class_group") val classGroup: String
    // Add other fields as necessary
)

// Model for the user response
data class UserCredentials(val email: String, val password: String)

data class UserResponse(val success: Boolean, val message: String, val token: String)

// Model for updating user profile data
data class ProfileData(
    val nickname: String,
    @SerializedName("class_group") val classGroup: String
    // Include other fields as necessary, like image if you're handling image uploads
)

// Model for the user profile response
data class UserProfileResponse(
    val email: String,
    val nickname: String,
    @SerializedName("class_group") val classGroup: String // Add this line
    // Add other fields as per your API response
)

data class UserCheckResponse(val exists: Boolean, val userId: Int?)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String   // Ensure this field exists and matches the JSON response from your backend
)