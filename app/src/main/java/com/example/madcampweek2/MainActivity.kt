package com.example.madcampweek2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampweek2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//바로 화면 넘어갈 수 있도록
//        checkLoginStatus()

        setBottomNavigationView()

        // Set initial fragment on app start
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment1_home
        }
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val isRegistered = sharedPreferences.getBoolean("isRegistered", false)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isRegistered) {
            navigateToRegister()
        } else if (!isLoggedIn) {
            navigateToLogin()
        }
        // If the user is registered and logged in, continue with MainActivity's setup
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment1_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_Container, Fragment1_Home()).commit()
                    true
                }

                R.id.fragment2_community -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_Container, Fragment2_Community()).commit()
                    true
                }

                R.id.fragment3_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_Container, Fragment3_Profile()).commit()
                    true
                }

                else -> false
            }
        }
    }

    // Example logout function (You need to call this from an appropriate place in your app)
    private fun logout() {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        navigateToLogin()
    }
}