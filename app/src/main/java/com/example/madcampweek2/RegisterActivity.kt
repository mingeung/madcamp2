package com.example.madcampweek2

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val name = binding.nameEditText.text.toString()

        if (validateInput(email, password, name)) {
            val newUser = NewUser(email, password, name)
            val call = RetrofitClient.retrofitInstance.registerUser(newUser)

            call.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        // Registration successful
                        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isRegistered", true)
                        editor.apply()

                        // Redirect to MainActivity or another activity
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Handle registration failure
                        Toast.makeText(this@RegisterActivity, "Registration failed: ${response.body()?.message}", Toast.LENGTH_LONG).show()
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

    private fun validateInput(email: String, password: String, name: String): Boolean {
        // Add your input validation logic here
        return email.isNotBlank() && password.isNotBlank() && name.isNotBlank()
    }
}