package com.app.easemypost.ui.dop.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentDashboardBinding
import com.app.easemypost.domain.model.response.TruckDetailsRes
import com.app.easemypost.ui.dop.viewmodel.DopViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter


class DashboardFragment<PieChart, PieEntry> : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    // Sample truck numbers
    private val dopViewModel by activityViewModels<DopViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initSetView()
        initClickListener()
        allTrucksObserver()
        truckDetailObserver()
    }

    private fun initSetView() {
        initPieChart()
        getAllTrucks()

    }

    private fun initClickListener() = binding.apply {


        binding.btnDeliveryInfo.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_deliveryInfoFragment)
        }

        binding.btnDeliveryPackageInfo.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_packageInfoFragment)
        }

        binding.btnSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_scheduleDeliveryFragment)
        }
        binding.btnCapacity.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_capacityFragment)
        }

    }

    private fun showPieChart(delivered: Int, toDeliver: Int) = binding.apply {
        val pieEntries = ArrayList<com.github.mikephil.charting.data.PieEntry>()
        val label = "Process"

        //initializing data
        val typeAmountMap: MutableMap<String, Double> = HashMap()
        typeAmountMap["To be Delivered"] = toDeliver.toDouble()
        typeAmountMap["Delivered"] = delivered.toDouble()

        //initializing colors for the entries
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))
        colors.add(Color.parseColor("#890567"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#3ca567"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PercentFormatter())
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)

        pieChart.setData(pieData)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.invalidate()
    }

    private fun initPieChart() = binding.apply {
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true)

        //remove the description label on the lower left corner, default true if not set
        pieChart.description.isEnabled = true
        pieChart.description.text = "Process"
        pieChart.description.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        //enabling the user to rotate the chart, default true
        pieChart.isRotationEnabled = true
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f)
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0F)
        pieChart.setDrawCenterText(true)
        pieChart.setDrawEntryLabels(true)
        pieChart.setDrawMarkers(false)

        pieChart.legend.isEnabled = true
        pieChart.legend.isWordWrapEnabled = true
        pieChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        //highlight the entry when it is tapped, default true if not set
        pieChart.isHighlightPerTapEnabled = true
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"))
    }

    private fun getAllTrucks() {
        dopViewModel.getAllTrucks("8826302576")
    }

    private fun setSpinner(trucks: ArrayList<String>) {
        trucks.add("Abcdgf")
        trucks.add("Abcdgf")
        trucks.add("Abcdgf")
        trucks.add("Abcdgf")
        trucks.add("Abcdgf")
        val spinnerTruckNumber = binding.spinnerTruck
        val adapter = ArrayAdapter(requireContext(), R.layout.spiner_item, trucks)
        adapter.setDropDownViewResource(R.layout.spiner_item)
        spinnerTruckNumber.adapter = adapter
        spinnerTruckNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected truck number
                val selectedTruckNumber = parentView.getItemAtPosition(position) as String
                dopViewModel.getTruckDetails("8826302576", selectedTruckNumber)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle case when no item is selected
            }
        }
    }

    private fun allTrucksObserver() {
        dopViewModel.allTrucks.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("DOP", res.data.toString())
                    var trucks = arrayListOf<String>()
                    for (i in 0..<res.data.size) {
                        trucks.add(res.data[i].truckId)
                    }
                    setSpinner(trucks)
                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
                        res.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("DOP", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("DOP", "Loading")
                }
            }
        }
    }

    private fun truckDetailObserver() {
        dopViewModel.truckDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("DOP", res.data.toString())
                    setData(res.data)
                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
                        res.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("DOP", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("DOP", "Loading")
                }
            }
        }
    }

    private fun setData(data: TruckDetailsRes) = binding.apply {
        tvTotalDeliveries.text = data.parcels.size.toString()
        var delivered = 0
        var toDeliver = 0
        for (i in 0..<data.parcels.size) {
            if(data.parcels[i].status == "Delivered"){
                delivered++
            }else{
                toDeliver++
            }

        }
        showPieChart(delivered, toDeliver)

    }

}


