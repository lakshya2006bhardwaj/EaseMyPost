package com.app.easemypost.ui.thirdParty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.easemypost.R
import com.app.easemypost.databinding.ActivityThirdPartyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdPartyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdPartyBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdPartyBinding.inflate((layoutInflater))
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tp) as NavHostFragment
        navController = navHostFragment.navController

      setupWithNavController(binding.bottomNavigationViewTp, navController)
        binding.bottomNavigationViewTp.itemIconTintList = null
        binding.bottomNavigationViewTp.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    navController.navigate(R.id.dashboard_tp)
                    true
                }

                R.id.tracking -> {
                    navController.navigate(R.id.tracking_tp)
                    true
                }

                R.id.dispatch -> {
                    navController.navigate(R.id.dispatch_tp)
                    true
                }

                R.id.alerts -> {
                    navController.navigate(R.id.alert_tp)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.profile_tp)
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
            binding.bottomNavigationViewTp.isVisible =
                topLevelDestination.contains(destination.id)
        }
//        val dataBase = FirebaseDatabase.getInstance()
//        val truckRef = dataBase.getReference("trucks")
//        val truckData = mapOf(
//            "driverName" to "Driver 1",
//            "truckNumber" to "ABC123",
//            "long" to 28.6193,
//            "lat" to 77.2090
//        )
//        truckRef.child("truck_1").setValue(truckData)
//            .addOnSuccessListener {
//                Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this,"Error ${it.message}",Toast.LENGTH_SHORT).show()
//            }
    }
}