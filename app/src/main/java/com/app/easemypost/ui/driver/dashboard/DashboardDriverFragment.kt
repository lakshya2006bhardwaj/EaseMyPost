package com.app.easemypost.ui.driver.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.data.local.LocationUpdateService
import com.app.easemypost.databinding.FragmentDashboardDriverBinding
import com.app.easemypost.ui.driver.ScannerActivity

class DashboardDriverFragment : Fragment() {
    private lateinit var binding: FragmentDashboardDriverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardDriverBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initSetViews()
        initClickListner()
    }

    private fun initSetViews() {
        // Set up views if needed
    }

    private fun initClickListner() {
        binding.btnScan.setOnClickListener {
            val intent = Intent(requireContext(), ScannerActivity::class.java)
            startActivity(intent)
        }

        // Example call for starting location updates
//        startLocationUpdates("truck_2")
    }

    private fun startLocationUpdates(adminId: String) {
        val serviceIntent = Intent(requireContext(), LocationUpdateService::class.java)
        serviceIntent.putExtra("driverId", adminId)
        requireContext().startService(serviceIntent)
    }
}
