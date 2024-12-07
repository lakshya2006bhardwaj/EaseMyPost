package com.app.easemypost.ui.DeliveryPartner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.easemypost.R
import com.app.easemypost.databinding.ActivityDeliveryPartnerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryPartnerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDeliveryPartnerBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDeliveryPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_dp -> {
                    navController.navigate(R.id.dashboard_dp)
                    true
                }

                R.id.tracking_dp -> {
                    navController.navigate(R.id.tracking_dp)
                    true
                }

                R.id.alerts_dp -> {
                    navController.navigate(R.id.alerts_dp)
                    true
                }
                R.id.profile_dp -> {
                    navController.navigate(R.id.profile_dp)
                    true
                }

                else -> false
            }
        }

        val topLevelDestination: Set<Int> = setOf(
          R.id.tracking_dp,
            R.id.profile_dp,
            R.id.dashboard_dp,
            R.id.alerts_dp
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible =
                topLevelDestination.contains(destination.id)
        }
    }
}