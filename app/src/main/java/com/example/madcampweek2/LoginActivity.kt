package com.example.madcampweek2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampweek2.databinding.ActivityLoginBinding
import com.example.madcampweek2.model.UserCheckResponse
import com.example.madcampweek2.network.ApiService
import com.example.madcampweek2.network.LoginResponse
import com.example.madcampweek2.network.RetrofitClient
import com.example.madcampweek2.network.UserCredentials
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }

        binding.registerButton.setOnClickListener {
            navigateToRegister()
        }
        binding.passButton.setOnClickListener {
            // 클릭 시 Fragment1_Home으로 이동
            navigateToFragment1Home()
        }
        binding.kakaoLoginButton.setOnClickListener {
            handleKakaoLogin()
        }
    }
    private fun handleKakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                // Handle login error
            } else if (token != null) {
                // Get user info from Kakao
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        // Handle error
                    } else if (user != null && user.kakaoAccount?.email != null) {
                        val email = user.kakaoAccount!!.email!!
                        checkUserInSystem(email)
                    }
                }
            }
        }
    }
    private fun checkUserInSystem(email: String) {
        val apiService = RetrofitClient.getInstance()
        apiService.checkUser(email).enqueue(object : Callback<UserCheckResponse> {
            override fun onResponse(
                call: Call<UserCheckResponse>,
                response: Response<UserCheckResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!
                    if (userResponse.exists) {
                        // User exists - proceed with login or further actions
                        handleExistingUser(userResponse.userId)
                    } else {
                        // User does not exist - redirect to registration
                        navigateToRegistration(email)
                    }
                } else {
                    // Handle API error
                    handleApiError()
                }
            }

            override fun onFailure(call: Call<UserCheckResponse>, t: Throwable) {
                // Handle network error
                handleNetworkError()
            }
        })
    }

    private fun handleExistingUser(userId: Int?) {
        // Implement logic to handle an existing user
        // This could be logging them in or taking some other action
    }

    private fun navigateToRegistration(email: String) {
        // Implement logic to navigate to the Registration Activity
        // Pass the email as an extra
    }

    private fun handleApiError() {
        // Handle API error scenario
    }

    private fun handleNetworkError() {
        // Handle network error scenario
    }

    private fun initiateKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                // Handle error scenario
            } else if (token != null) {
                // Handle successful login, navigate to MainActivity
                navigateToMainActivity()
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun performLogin(email: String, password: String) {
        val credentials = UserCredentials(email, password)

        RetrofitClient.getInstance().loginUser(credentials).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Save login status
                    val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    // Navigate to MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    //바로 넘어가는 버튼
    private fun navigateToFragment1Home() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}