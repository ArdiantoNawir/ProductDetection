package com.capstone.productdetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.productdetection.databinding.ActivityMainBinding
import com.capstone.productdetection.ui.capture.CaptureActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)
        binding.navView.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when(it.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_favorite -> {
                    navController.navigate(R.id.navigation_favorite)
                    true
                }
                else -> false
            }
        }

        binding.navView.background = null

        binding.faButton.setOnClickListener {
                val intent = Intent(this, CaptureActivity::class.java)
                startActivity(intent)
        }

    }

}
