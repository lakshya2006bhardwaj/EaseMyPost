package com.app.easemypost.ui.DeliveryPartner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.easemypost.R
import com.app.easemypost.databinding.ActivityDeliveryPartnerBinding

class DeliveryPartnerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDeliveryPartnerBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityDeliveryPartnerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    navController.navigate(R.id.dashboard)
                    true
                }

                R.id.addDriver -> {
                    navController.navigate(R.id.addDriver)
                    true
                }

                R.id.addTruck -> {
                    navController.navigate(R.id.addTruck)
                    true
                }

                R.id.alerts -> {
                    navController.navigate(R.id.alerts)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.profile)
                    true
                }

                else -> false
            }
        }

        val topLevelDestination: Set<Int> = setOf(
            R.id.addTruck,
            R.id.addDriver,
            R.id.profile,
            R.id.dashboard,
            R.id.alerts
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible =
                topLevelDestination.contains(destination.id)
        }
    }
}