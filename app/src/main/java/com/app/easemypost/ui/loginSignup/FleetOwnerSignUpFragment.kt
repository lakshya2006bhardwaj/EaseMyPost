package com.app.easemypost.ui.loginSignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentFleetOwnerSignUpBinding

class FleetOwnerSignUpFragment : Fragment() {
    private lateinit var binding:FragmentFleetOwnerSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFleetOwnerSignUpBinding.inflate(inflater)
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