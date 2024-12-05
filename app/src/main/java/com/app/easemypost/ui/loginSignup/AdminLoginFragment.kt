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
import com.app.easemypost.databinding.FragmentAdminLoginBinding
import com.app.easemypost.domain.model.requests.AdminLoginReq
import com.app.easemypost.ui.loginSignup.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLoginFragment : Fragment() {
    private lateinit var binding: FragmentAdminLoginBinding
    private val authViewModel by activityViewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminLoginBinding.inflate(inflater)
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
        adminLoginDataObserver()
    }

    private fun initSetView() {

    }

    private fun initClickListener()=binding.apply{
        binding.btnSignup.setOnClickListener() {
            if(!etMobileNo.text.isNullOrEmpty()){
                authViewModel.adminLogin(
                    AdminLoginReq(
                        identifier = etMobileNo.text.toString()
                    )
                )
            }
            else{
                Toast.makeText(requireContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun adminLoginDataObserver() {
        authViewModel.adminLoginDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    findNavController().navigate(R.id.action_adminLoginFragment_to_dopSignUpFragment2)
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


