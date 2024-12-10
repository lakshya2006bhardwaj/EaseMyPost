package com.app.easemypost.ui.dop.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentScheduleDeliveryNextPageBinding
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.ParcelStatus
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.ui.dop.DopActivity
import com.app.easemypost.ui.dop.viewmodel.DopViewModel

class ScheduleDeliveryNextPageFragment : Fragment() {
    private lateinit var binding:FragmentScheduleDeliveryNextPageBinding
    private var requiredCapacity:String?=null
    private var city:String?=null
    private val dopViewModel by activityViewModels<DopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding=FragmentScheduleDeliveryNextPageBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        intSetViews()
        initClickListener()
        adminLoginDataObserver()
        scheduleDeliveryObserver()
    }

    private fun intSetViews(){
        city = dopViewModel.scheduleDeliveryData?.pickupLocation?.let { extractCityFromAddress(it) }
        requiredCapacity = (dopViewModel.scheduleDeliveryData?.height?.toDouble()!! * dopViewModel.scheduleDeliveryData?.length?.toDouble()!! * dopViewModel.scheduleDeliveryData?.breadth?.toDouble()!!).toString()
        getDriversAndFleet()
    }

    private fun initClickListener(){

    }
    private fun getDriversAndFleet(){
        city?.let {
            requiredCapacity?.let { it1 ->
                DriversAndFleetOwnersReq(
                    baseLocation = it,
                    requiredCapacity = it1,
                    owner = "dop"
                )
            }
        }?.let {
            dopViewModel.getDriverAndFleetOwners(
                it
            )
        }
    }
    private fun extractCityFromAddress(address: String): String {
        val cities = listOf(
            "Delhi", "New Delhi", "Agra", "Lucknow", "Kanpur", "Varanasi", "Allahabad", "Ghaziabad", "Noida", "Meerut",
            "Aligarh", "Amritsar", "Ludhiana", "Chandigarh", "Jalandhar", "Patiala", "Gurgaon", "Faridabad", "Panipat",
            "Karnal", "Sonipat", "Jaipur", "Jodhpur", "Udaipur", "Kota", "Ajmer", "Bikaner", "Shimla", "Dharamshala",
            "Manali", "Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Ahmedabad", "Surat", "Vadodara", "Rajkot",
            "Gandhinagar", "Panaji", "Vasco da Gama", "Bengaluru", "Mysore", "Mangalore", "Hubli", "Chennai", "Coimbatore",
            "Madurai", "Salem", "Visakhapatnam", "Vijayawada", "Guntur", "Hyderabad", "Warangal", "Kochi",
            "Thiruvananthapuram", "Kozhikode", "Kolkata", "Siliguri", "Durgapur", "Asansol", "Bhubaneswar", "Cuttack",
            "Rourkela", "Patna", "Gaya", "Muzaffarpur", "Ranchi", "Jamshedpur", "Dhanbad", "Guwahati", "Dibrugarh",
            "Silchar", "Shillong", "Kohima", "Dimapur", "Agartala", "Bhopal", "Indore", "Gwalior", "Jabalpur", "Raipur",
            "Bilaspur","Haryana"
        )
        for (city in cities) {
            if (address.contains(city, ignoreCase = true)) {
                return city
            }
        }
        return "City not found"
    }
    private fun adminLoginDataObserver() {
        dopViewModel.driverAndFleetOwnersDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                    dopViewModel.scheduleDeliveryData?.status = ParcelStatus.In_Transit
                    dopViewModel.scheduleDeliveryData?.let {
                        ScheduleDeliveryReq(
                            driverId ="67552fec82c5b338adf74a98",
                            parcelInfo = it
                        )
                    }?.let {
                        dopViewModel.scheduleDelivery(
                            it
                        )
                    }

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
    private fun scheduleDeliveryObserver() {
        dopViewModel.scheduleDelivery.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                    dopViewModel.qrCode = res.data.parcel.qrCode.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        dopViewModel.clearData()
    }

}