package com.app.easemypost.ui.loginSignup

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
import com.app.easemypost.databinding.FragmentDopSignUpBinding
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.ui.loginSignup.viewmodel.AuthViewModel

class DopSignUpFragment : Fragment() {
    private lateinit var binding: FragmentDopSignUpBinding
    private val authViewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDopSignUpBinding
            .inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    private fun init() {
        initSetView()
        initClickListener()
        adminSignUpDataObserver()
    }

    private fun initSetView() {

    }

    private fun initClickListener() = binding.apply {
        btnDopSignup.setOnClickListener {
            if (!etName.text.isNullOrEmpty()) {
                if (!etEmail.text.isNullOrEmpty()) {
                    if (!etMobileNo.text.isNullOrEmpty()) {
                        if (!etPincode.text.isNullOrEmpty()) {
                            if (!etHubCode.text.isNullOrEmpty()) {
                                if (!etHubAddrress.text.isNullOrEmpty()) {
                                    if (!etHubName.text.isNullOrEmpty()) {
                                        if (tvTermsConditions.isChecked) {
                                            authViewModel.phone = etMobileNo.text.toString()
                                            authViewModel.adminSignUp(
                                                AdminSignUpReq(
                                                    stationName = etHubName.text.toString(),
                                                    stationCode = etHubCode.text.toString(),
                                                    address = etHubAddrress.text.toString(),
                                                    pinCode = etPincode.text.toString(),
                                                    adminName = etName.text.toString(),
                                                    email = etEmail.text.toString(),
                                                    phone = etMobileNo.text.toString(),
                                                    agreementConfirmation = true,
                                                    otp = "",
                                                    otpExpiry = "",
                                                    isVerified = false
                                                )
                                            )
                                        }
                                        else{
                                            Toast.makeText(
                                                requireContext(),
                                                "Please Accept Terms and Conditions",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    else{
                                        Toast.makeText(
                                            requireContext(),
                                            "Please Enter Hub Name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                else{
                                    Toast.makeText(
                                        requireContext(),
                                        "Please Enter Hub Address",
                                        Toast.LENGTH_SHORT
                                        ).show()
                                }
                            }
                            else {
                                Toast.makeText(
                                    requireContext(),
                                    "Please Enter Hub Code",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        else {
                            Toast.makeText(
                                requireContext(),
                                "Please Enter Pincode",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            "Please Enter Mobile Number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        "Please Enter Email Address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    requireContext(),
                    "Please Enter Name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnDopSignup.setOnClickListener(){
            findNavController().navigate(R.id.action_dopSignUpFragment_to_verifyOtpFragment)
        }

    }

    private fun adminSignUpDataObserver() {
        authViewModel.adminSignUpDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Toast.makeText(
                        requireContext(),
                        res.data.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("AuthAdmin", res.data.message)
                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
                        res.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("AuthAdmin", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("AuthAdmin", "Loading")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.clearData()
    }

}