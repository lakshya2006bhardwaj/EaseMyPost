package com.app.easemypost.ui.dop.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentScheduleDeliveryNextPageBinding
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.ExpandableData
import com.app.easemypost.domain.model.requests.ParcelStatus
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.domain.model.response.AssignedData
import com.app.easemypost.domain.model.response.DriverRes
import com.app.easemypost.ui.dop.viewmodel.DopViewModel
import com.app.easemypost.ui.dop.dashboard.adapter.ExpandableListAdapter1
import com.app.easemypost.ui.dop.dashboard.adapter.ExpandableListAdapter2

class ScheduleDeliveryNextPageFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDeliveryNextPageBinding
    private var requiredCapacity: String? = null
    private var city: String? = null
    private val dopViewModel by activityViewModels<DopViewModel>()
    private var driversDetails = arrayListOf<AssignedData>()

    // Sample data for the first expandable list (driver names and truck numbers)
    private val driverNames = arrayListOf<ExpandableData>()


    // Sample data for the second expandable list (fleet company names)
    private val fleetCompanies = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDeliveryNextPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        intSetViews()
        initClickListener()
        adminLoginDataObserver()
        scheduleDeliveryObserver()
        initExpandableLists(driverNames)
    }

    private fun intSetViews() {
        driverNames.clear()
        city = dopViewModel.scheduleDeliveryData?.pickupLocation?.let { extractCityFromAddress(it) }
        requiredCapacity =
            (dopViewModel.scheduleDeliveryData?.height?.toDouble()!! * dopViewModel.scheduleDeliveryData?.length?.toDouble()!! * dopViewModel.scheduleDeliveryData?.breadth?.toDouble()!!).toString()
        getDriversAndFleet()
    }

    private fun initClickListener() {

    }

    private fun getDriversAndFleet() {
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
            "Delhi",
            "New Delhi",
            "Agra",
            "Lucknow",
            "Kanpur",
            "Varanasi",
            "Allahabad",
            "Ghaziabad",
            "Noida",
            "Meerut",
            "Aligarh",
            "Amritsar",
            "Ludhiana",
            "Chandigarh",
            "Jalandhar",
            "Patiala",
            "Gurgaon",
            "Faridabad",
            "Panipat",
            "Karnal",
            "Sonipat",
            "Jaipur",
            "Jodhpur",
            "Udaipur",
            "Kota",
            "Ajmer",
            "Bikaner",
            "Shimla",
            "Dharamshala",
            "Manali",
            "Mumbai",
            "Pune",
            "Nagpur",
            "Nashik",
            "Aurangabad",
            "Ahmedabad",
            "Surat",
            "Vadodara",
            "Rajkot",
            "Gandhinagar",
            "Panaji",
            "Vasco da Gama",
            "Bengaluru",
            "Mysore",
            "Mangalore",
            "Hubli",
            "Chennai",
            "Coimbatore",
            "Madurai",
            "Salem",
            "Visakhapatnam",
            "Vijayawada",
            "Guntur",
            "Hyderabad",
            "Warangal",
            "Kochi",
            "Thiruvananthapuram",
            "Kozhikode",
            "Kolkata",
            "Siliguri",
            "Durgapur",
            "Asansol",
            "Bhubaneswar",
            "Cuttack",
            "Rourkela",
            "Patna",
            "Gaya",
            "Muzaffarpur",
            "Ranchi",
            "Jamshedpur",
            "Dhanbad",
            "Guwahati",
            "Dibrugarh",
            "Silchar",
            "Shillong",
            "Kohima",
            "Dimapur",
            "Agartala",
            "Bhopal",
            "Indore",
            "Gwalior",
            "Jabalpur",
            "Raipur",
            "Bilaspur",
            "Haryana"
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

                    var drivers = arrayListOf<String>()
                    if (res.data.drivers != null) {
                        for (i in 0..<res.data.drivers.size) {
                            driversDetails.add(res.data.drivers[i])
                            drivers.add(res.data.drivers[i].driverName)
                        }
                    }
                    driverNames.add(
                        ExpandableData(
                            title = "Select Drivers",
                            subList = drivers
                        )
                    )
                    var fleets = arrayListOf<String>()
                    if (res.data.fleetOwners != null) {
                        for (i in 0..<res.data.fleetOwners.size)
                            fleets.add(res.data.fleetOwners[i])
                    }
                    driverNames.add(
                        ExpandableData(
                            title = "Select Fleet Owners",
                            subList = fleets
                        )
                    )
                    initExpandableLists(driverNames)

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

    private fun initExpandableLists(driverNames: ArrayList<ExpandableData>) {
        // Initialize the first expandable list for drivers and trucks
        val adapter1 =
            ExpandableListAdapter1(driverNames, requireContext()) { groupPosition, childPosition ->
                onItemClick(groupPosition, childPosition)
            }
        binding.expandableListView1.setAdapter(adapter1)

//        // Initialize the second expandable list for fleet company names
//        val adapter2 = ExpandableListAdapter2(requireContext(), fleetCompanies)
//        binding.expandableListView2.setAdapter(adapter2)

    }

    private fun onItemClick(groupPosition: Int, childPosition: Int?) {
        val item = driverNames[groupPosition]
        if (childPosition != null) {
            if (item == driverNames[0]) {
                dopViewModel.scheduleDeliveryData?.let {
                    ScheduleDeliveryReq(
                        driverId = driversDetails[childPosition].driverId,
                        parcelInfo = it
                    )
                }?.let {
                    dopViewModel.scheduleDelivery(
                        it
                    )
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
                    findNavController().navigate(R.id.action_scheduleDeliveryNextPageFragment_to_dispatch)
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
