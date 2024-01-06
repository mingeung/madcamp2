package com.example.madcampweek2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.madcampweek2.model.UserProfileResponse
import com.example.madcampweek2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment3_Profile : Fragment() {

    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment3_profile, container, false)

        usernameTextView = view.findViewById(R.id.usernameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)

        fetchUserProfile()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        // Assuming you have a Retrofit call set up to fetch the user profile
        RetrofitClient.retrofitInstance.getUserProfile() // Replace with your actual method
            .enqueue(object : Callback<UserProfileResponse> { // Replace UserProfileResponse with your response model
                override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                    if (response.isSuccessful) {
                        val userProfile = response.body()
                        // Update UI with user profile data
                        userProfile?.let {
                            usernameTextView.text = it.nickname  // Assuming 'nickname' field in your response
                            emailTextView.text = it.email      // Assuming 'email' field in your response
                        }
                    } else {
                        // Handle errors
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    // Handle network errors
                }
            })
    }
}