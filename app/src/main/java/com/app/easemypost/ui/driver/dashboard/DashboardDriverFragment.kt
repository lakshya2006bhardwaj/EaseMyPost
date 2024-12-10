package com.app.easemypost.ui.driver.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentDashboardDriverBinding

class DashboardDriverFragment : Fragment() {
    private lateinit var binding:FragmentDashboardDriverBinding
       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
           binding=FragmentDashboardDriverBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        initSetViews()
        initClickListner()
    }

    private fun initSetViews(){

    }

    private fun initClickListner(){

    }





}