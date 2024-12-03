package com.app.easemypost.ui.dop.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentCapacityBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class CapacityFragment : Fragment() {
    private var _binding: FragmentCapacityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCapacityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initSetViews()
        initClickListeners()
    }

    private fun initSetViews() {
        // Example capacity percentage
        val filledPercentage = 75

        // Update the text below the chart
        binding.tvDopCapacity.text = "$filledPercentage% filled"

        // Configure and display the pie chart
        setupPieChart(filledPercentage)
    }

    private fun initClickListeners() {
        // Add click listeners if required
    }

    private fun setupPieChart(filledPercentage: Int) {
        val emptyPercentage = 100 - filledPercentage

        // Add entries for the pie chart
        val entries = listOf(
            PieEntry(filledPercentage.toFloat(), "Filled"),
            PieEntry(emptyPercentage.toFloat(), "Empty")
        )

        val dataSet = PieDataSet(entries, "Truck Capacity")
        dataSet.colors = listOf(
            ColorTemplate.rgb("#4CAF50"), // Green for filled
            ColorTemplate.rgb("#BDBDBD") // Gray for empty
        )

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        binding.pieChart.data = data

        // Customize the pie chart
        binding.pieChart.setUsePercentValues(false)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        binding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        binding.pieChart.holeRadius = 50f // Hole in the center
        binding.pieChart.transparentCircleRadius = 55f // Slightly bigger than the hole
        binding.pieChart.invalidate() // Refresh chart
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
