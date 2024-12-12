package com.app.easemypost.ui.driver.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.easemypost.data.local.LocationUpdateService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentDashboardDriverBinding
import com.app.easemypost.ui.driver.ScannerActivity
import com.app.easemypost.domain.model.requests.DriverLoginReq
import com.app.easemypost.ui.driver.viewmodel.DriverViewModel

class DashboardDriverFragment : Fragment() {
    private lateinit var binding:FragmentDashboardDriverBinding
    private val driverViewModel by activityViewModels<DriverViewModel>()
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

    private fun init(){
        initSetViews()
        initClickListner()
        driverLoginObserver()
    }

    private fun initSetViews(){
        binding.btnLogin.setOnClickListener {
            driverViewModel.driverLogin(
                DriverLoginReq(
                    phone = binding.etMobileNo.text.toString()
                )
            )
        }
    private fun initSetViews() {
        // Set up views if needed
    }

        private fun initClickListner() {
            binding.btnScan.setOnClickListener {
                val intent = Intent(requireContext(), ScannerActivity::class.java)
                startActivity(intent)
            }
    private fun driverLoginObserver() {
        driverViewModel.driverLogin.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                    Toast.makeText(requireContext(), res.data.message, Toast.LENGTH_SHORT).show()
                    binding.btnLogin.visibility = View.GONE
                    binding.etMobileNo.visibility = View.GONE
                    binding.tvEnterMobile.visibility = View.GONE
                }


                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
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
    private fun startLocationUpdates(adminId: String) {
        val serviceIntent = Intent(requireContext(), LocationUpdateService::class.java)
        serviceIntent.putExtra("driverId", adminId)
        requireContext().startService(serviceIntent)
    }
}
