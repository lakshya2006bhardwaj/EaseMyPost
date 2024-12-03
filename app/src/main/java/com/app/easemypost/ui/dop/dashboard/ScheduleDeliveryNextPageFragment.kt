package com.app.easemypost.ui.dop.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentScheduleDeliveryNextPageBinding

class ScheduleDeliveryNextPageFragment : Fragment() {
    private lateinit var binding:FragmentScheduleDeliveryNextPageBinding

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
        initClickListner()
    }

    private fun intSetViews(){

    }

    private fun initClickListner(){

    }


  }