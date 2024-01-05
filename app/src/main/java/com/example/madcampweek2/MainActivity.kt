package com.example.madcampweek2

import SectionsPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madcampweek2.databinding.ActivityMainBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment1_home
        }
    }

    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            try {
                when (item.itemId) {
                    R.id.fragment1_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_Container, Fragment1_Home()).commit()
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.fragment2_community -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_Container, Fragment2_Community()).commit()
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.fragment3_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_Container, Fragment3_Profile()).commit()
                        return@setOnNavigationItemSelectedListener true
                    }

                    else -> return@setOnNavigationItemSelectedListener false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnNavigationItemSelectedListener false
            }
        }
    }
}