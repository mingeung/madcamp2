package com.example.madcampweek2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampweek2.databinding.ActivityProfileSetupBinding
import com.example.madcampweek2.model.ProfileData
import com.example.madcampweek2.network.RetrofitClient
import com.example.madcampweek2.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nicknameEditText: EditText = binding.nicknameEditText
        val githubUrlEditText: EditText = binding.githubUrlEditText
        val classGroupSpinner: Spinner = binding.classGroupSpinner
        val uploadImageButton: Button = binding.uploadImageButton
        val submitProfileButton: Button = binding.submitProfileButton

        // Add listeners and logic for image upload and form submission

        submitProfileButton.setOnClickListener {
            val nickname = nicknameEditText.text.toString()
            val githubUrl = githubUrlEditText.text.toString()
            val classGroup = classGroupSpinner.selectedItem.toString()

            val profileData = ProfileData(nickname, githubUrl, classGroup)
            RetrofitClient.retrofitInstance.updateProfile(profileData)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            // Handle successful profile update
                            val intent = Intent(this@ProfileSetupActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle errors
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Handle network errors
                    }
                })
        }

        // TODO: Add logic for image upload button
    }
}