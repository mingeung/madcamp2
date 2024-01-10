package com.example.madcampweek2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.madcampweek2.model.UserProfileResponse
import com.example.madcampweek2.model.UserResponse
import com.example.madcampweek2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.*

class Fragment3_Profile : Fragment() {

    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var groupNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment3_profile, container, false)

        usernameTextView = view.findViewById(R.id.usernameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        groupNumberTextView = view.findViewById(R.id.groupNumberTextView)

        return view
    }

    private lateinit var logoutButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { performLogout() }
        fetchUserProfile()
    }
    private fun performLogout() {
        RetrofitClient.getInstance(requireContext()).logoutUser().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    // Clear any stored authentication tokens or user data
                    val sharedPrefs = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                    sharedPrefs.edit().remove("authToken").apply()

                    // Start LoginActivity and clear the activity stack
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    // Handle API error
                    Log.e("Logout", "Failed to logout: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // Handle network error
                Log.e("Logout", "Network error", t)
            }
        })
    }

    private fun fetchUserProfile() {
        context?.let {
            RetrofitClient.getInstance(requireContext()).getUserProfile()
                .enqueue(object : Callback<UserProfileResponse> {
                    override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                        if (response.isSuccessful) {
                            val userProfile = response.body()
                            Log.d("ProfileFragment", "Profile data: $userProfile")

                            // Update UI with the user profile data
                            usernameTextView.text = userProfile?.nickname ?: "N/A"
                            emailTextView.text = userProfile?.email ?: "N/A"
                            groupNumberTextView.text = userProfile?.classGroup ?: "N/A"
                        } else {
                            Log.e("ProfileFragment", "Error: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                        Log.e("ProfileFragment", "Network error", t)
                    }
                })
        }
    }
}