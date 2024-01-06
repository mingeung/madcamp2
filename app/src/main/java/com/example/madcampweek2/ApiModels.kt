package com.example.madcampweek2.model

import com.google.gson.annotations.SerializedName

// Replace with your actual package name

// Model for creating a new user
data class NewUser(
    val email: String,
    val password: String,
    val name: String
    // Add other fields as necessary
)

// Model for the user response
data class UserResponse(
    val success: Boolean,
    val message: String
    // Include other response fields as needed
)

// Model for updating user profile data
data class ProfileData(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("github_url") val githubUrl: String,
    @SerializedName("class_group") val classGroup: String
    // Include other fields as necessary, like image if you're handling image uploads
)

data class UserProfileResponse(
    val email: String,
    val nickname: String,
    // Add other fields as per your API response
)