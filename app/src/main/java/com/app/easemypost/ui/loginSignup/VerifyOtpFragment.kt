package com.app.easemypost.ui.loginSignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentVerifyOtpBinding
import com.app.easemypost.domain.model.requests.AdminVerifyOtpReq
import com.app.easemypost.ui.dop.DopActivity
import com.app.easemypost.ui.loginSignup.viewmodel.AuthViewModel

class VerifyOtpFragment : Fragment() {
    private lateinit var binding:FragmentVerifyOtpBinding
    private val authViewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentVerifyOtpBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initSetViews()
        initClickListener()
        adminVerifyOtpObserver()
    }

    private fun initSetViews(){

    }

    private fun initClickListener()=binding.apply{
        btnVerify.setOnClickListener {


            if (!etOtp.text.isNullOrEmpty() && etOtp.text.toString().length == 6) {
                authViewModel.adminVerifyOtp(
                    verifyOtpData = AdminVerifyOtpReq(
                        phone = authViewModel.phone,
                        otp = etOtp.text.toString()
                    )
                )
            }
        }

    }
    private fun adminVerifyOtpObserver() {
        authViewModel.adminVerifyOtpDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Toast.makeText(
                        requireContext(),
                        res.data.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(),DopActivity::class.java))
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