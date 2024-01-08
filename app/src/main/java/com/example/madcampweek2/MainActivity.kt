package com.example.madcampweek2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madcampweek2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        val isRegistered = sharedPreferences.getBoolean("isRegistered", false)

        if (isFirstTime || !isRegistered) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstTime", false)
            editor.apply()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setBottomNavigationView()

        // Set initial fragment on app start
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment1_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            try {
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
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}