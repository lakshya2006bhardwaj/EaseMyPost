package com.app.easemypost.ui.dop.dashboard

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.easemypost.databinding.FragmentCapacityBinding
import com.app.easemypost.ui.dop.viewmodel.DopViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class CapacityFragment : Fragment() {
    private var _binding: FragmentCapacityBinding? = null
    private val dopViewModel by activityViewModels<DopViewModel>()
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

    private fun initSetViews()= _binding?.apply {
        val totalCapacity = dopViewModel.truckDetailsData?.truck?.capacity
        val availableCapacity = dopViewModel.truckDetailsData?.truck?.availableCapacity
        if (totalCapacity != null && availableCapacity != null) {
            val usedCapacity = totalCapacity - availableCapacity
            val percentageUsed = (usedCapacity.toFloat() / totalCapacity.toFloat()) * 100

            circularProgressBar.max = 100

            val animator = ObjectAnimator.ofInt(circularProgressBar, "progress", 0, percentageUsed.toInt())
            animator.duration = 1000
            animator.interpolator = DecelerateInterpolator()
            animator.start()

            tvInfo.text =
                "${100-percentageUsed.toInt()}% capacity \nleft unused in Truck \n${dopViewModel.truckDetailsData?.truck?.truckId}"
            tvPercentage.text = "${percentageUsed.toInt()}%"
        } else {
            tvInfo.text = "Capacity details unavailable"
            tvPercentage.text = "N/A"
        }
    }

    private fun initClickListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
