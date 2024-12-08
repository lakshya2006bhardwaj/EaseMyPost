package com.app.easemypost.ui.dop.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentScheduleDeliveryNextPageBinding
import com.app.easemypost.ui.dop.dashboard.adapter.ExpandableListAdapter1
import com.app.easemypost.ui.dop.dashboard.adapter.ExpandableListAdapter2

class ScheduleDeliveryNextPageFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDeliveryNextPageBinding

    // Sample data for the first expandable list (driver names and truck numbers)
    private val driverNames = listOf("Driver A", "Driver B", "Driver C")
    private val truckNumbers = listOf(
        listOf("Truck A1", "Truck A2"),
        listOf("Truck B1"),
        listOf("Truck C1", "Truck C2")
    )

    // Sample data for the second expandable list (fleet company names)
    private val fleetCompanies = listOf("Fleet X", "Fleet Y", "Fleet Z")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDeliveryNextPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExpandableLists()
    }

    private fun initExpandableLists() {
        // Initialize the first expandable list for drivers and trucks
        val adapter1 = ExpandableListAdapter1(requireContext(), driverNames, truckNumbers)
        binding.expandableListView1.setAdapter(adapter1)

        // Initialize the second expandable list for fleet company names
        val adapter2 = ExpandableListAdapter2(requireContext(), fleetCompanies)
        binding.expandableListView2.setAdapter(adapter2)
    }
}
