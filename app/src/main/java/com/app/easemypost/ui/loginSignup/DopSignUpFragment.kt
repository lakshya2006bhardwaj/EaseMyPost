package com.app.easemypost.ui.loginSignup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentDopSignUpBinding
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.ui.loginSignup.viewmodel.AuthViewModel

class DopSignUpFragment : Fragment() {
    private lateinit var binding:FragmentDopSignUpBinding
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentDopSignUpBinding
            .inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    private fun init(){
        initSetView()
        initClickListener()
        adminSignUpDataObserver()
    }

    private fun initSetView(){

    }

    private fun initClickListener(){

        binding.btnDopSignup.setOnClickListener(){
            authViewModel.getRechargePlansDetails(
                AdminSignUpReq(
                    stationName = "Greenfield Station",
                    stationCode = "GRF123",
                    address = "123 Greenfield Lane, Cityville",
                    pinCode = "201002",
                    adminName = "John Doe",
                    email = "johndoe@example.com",
                    phone = "8826302576",
                    agreementConfirmation = true,
                    otp = "",
                    otpExpiry = "",
                    isVerified = false
                )
            )
        }


    }

    private fun adminSignUpDataObserver() {
        authViewModel.adminSignUpDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Toast.makeText(requireContext(),res.data.message,Toast.LENGTH_SHORT).show()
                    Log.d("AuthAdmin", res.data.message)
                }

                is ApiHandler.Error -> {
                    Toast.makeText(requireContext(), res.errorMessage,Toast.LENGTH_SHORT).show()
                    res.errorMessage?.let { Log.d("AuthAdmin", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("AuthAdmin", "Loading")
                }
            }
        }
    }

}