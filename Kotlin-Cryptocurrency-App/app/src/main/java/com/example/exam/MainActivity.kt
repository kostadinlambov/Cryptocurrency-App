package com.example.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.util.NetworkUtil
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController = binding.container.getFragment<NavHostFragment>().navController

        setupActionBarWithNavController(navController)

        if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    // Implement back button in the navbar
    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.container.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}