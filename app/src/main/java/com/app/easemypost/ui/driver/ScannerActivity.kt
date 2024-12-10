package com.app.easemypost.ui.driver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.easemypost.R
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.ui.driver.viewmodel.DriverViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerActivity : AppCompatActivity() {
    private val driverViewModel by viewModels<DriverViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        // Start scanner
        IntentIntegrator(this).apply {
            setOrientationLocked(false)
            setPrompt("Scan QR Code")
            initiateScan()
        }
        scheduleDeliveryObserver()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // Use the scanned result
                Toast.makeText(this, "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()
                driverViewModel.checkInDetails(
                    CheckInReq(
                        qrData = result.contents,
                        adminPhone = "8826302576"
                    )
                )
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun scheduleDeliveryObserver() {
        driverViewModel.checkInDetails.observe(this) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                       this@ScannerActivity,
                        res.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("ScheduleDelivery", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("ScheduleDelivery", "Loading")
                }
            }
        }
    }
}