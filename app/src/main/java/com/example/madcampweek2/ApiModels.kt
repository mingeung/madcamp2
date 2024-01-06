package com.example.madcampweek2.model  // Replace with your package name

data class NewUser(
    val email: String,
    val password: String,
    val name: String
    // Add other fields as necessary
)

data class UserResponse(
    val success: Boolean,
    val message: String
    // Include other response fields as needed
)