package com.app.easemypost.ui.driver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.easemypost.R
import com.app.easemypost.databinding.ActivityDriverMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverMainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDriverMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDriverMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_driver -> {
                    navController.navigate(R.id.dashboard_driver)
                    true
                }

                R.id.see_route_driver -> {
                    navController.navigate(R.id.see_route_driver)
                    true
                }

                R.id.alerts_driver -> {
                    navController.navigate(R.id.alerts_driver)
                    true
                }
                R.id.profile_driver -> {
                    navController.navigate(R.id.profile_driver)
                    true
                }

                else -> false
            }
        }

        val topLevelDestination: Set<Int> = setOf(
            R.id.dashboard_driver,
            R.id.see_route_driver,
            R.id.alerts_driver,
            R.id.profile_driver,
            )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible =
                topLevelDestination.contains(destination.id)
        }

    }
}