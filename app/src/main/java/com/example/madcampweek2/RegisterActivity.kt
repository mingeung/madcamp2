package com.example.madcampweek2

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampweek2.databinding.ActivityRegisterBinding
import com.example.madcampweek2.model.NewUser
import com.example.madcampweek2.model.UserResponse
import com.example.madcampweek2.network.RetrofitClient

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onRegisterClicked(view: View) {
        // Extract user input from EditText fields
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val name = binding.nameEditText.text.toString()
        val nickname = binding.nicknameEditText.text.toString()
        val classGroupSpinner = binding.classGroupSpinner

        // Extract the selected class group from the Spinner
        val classGroupIndex = binding.classGroupSpinner.selectedItemPosition + 1
        val classGroup = classGroupIndex.toString()

        val newUser = NewUser(email, password, name, nickname, classGroup)

        if (validateInput(email, password, name, nickname, classGroup)) {
            // Make a Retrofit API call to register the user
            val call = RetrofitClient.getInstance(this).registerUser(newUser)

            call.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        // Optionally set isRegistered flag if needed
                        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isRegistered", true)
                        editor.apply()

                        // Navigate to MainActivity
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Handle registration failure with specific error message
                        Toast.makeText(this@RegisterActivity, "Registration failed: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Handle network errors
                    Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(email: String, password: String, name: String, nickname: String, classGroup: String): Boolean {
        // Add your input validation logic here
        return email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && nickname.isNotBlank() && classGroup.isNotBlank()
    }
}