package com.app.easemypost.ui.dop.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentDashboardDopTruckAddBinding

class DashboardDopTruckAddFragment : Fragment() {
    private lateinit var binding:FragmentDashboardDopTruckAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        var bindng = FragmentDashboardDopTruckAddBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initClickListner()
        initSetViews()
    }
    private fun initSetViews(){

    }

    private fun initClickListner(){

    }

}