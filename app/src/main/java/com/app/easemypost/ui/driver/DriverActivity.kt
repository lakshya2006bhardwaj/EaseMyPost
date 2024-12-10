package com.app.easemypost.ui.driver

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.easemypost.data.local.LocationUpdateService
import com.app.easemypost.databinding.ActivityDriverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScan.setOnClickListener{
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }
        startLocationUpdates("truck_2")
    }
    private fun startLocationUpdates(adminId: String) {
        val serviceIntent = Intent(this, LocationUpdateService::class.java)
        serviceIntent.putExtra("driverId", adminId)
        startService(serviceIntent)
    }
}