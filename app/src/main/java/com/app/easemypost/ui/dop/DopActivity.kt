package com.app.easemypost.ui.dop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.easemypost.R
import com.app.easemypost.data.local.LocationUpdateService
import com.app.easemypost.databinding.ActivityDopMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDopMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDopMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    navController.navigate(R.id.dashboard)
                    true
                }

                R.id.tracking -> {
                    navController.navigate(R.id.tracking)
                    true
                }

                R.id.dispatch -> {
                    navController.navigate(R.id.dispatch)
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
            R.id.dispatch,
            R.id.tracking,
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