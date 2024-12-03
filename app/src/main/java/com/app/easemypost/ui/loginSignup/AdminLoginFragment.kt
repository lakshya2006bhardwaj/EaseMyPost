package com.app.easemypost.ui.loginSignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentAdminLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLoginFragment : Fragment() {
    private lateinit var binding: FragmentAdminLoginBinding

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
    }

    private fun initSetView() {

    }

    private fun initClickListener() {

        binding.btnSignup.setOnClickListener() {
            findNavController().navigate(R.id.action_adminLoginFragment_to_dopSignUpFragment2)
        }

    }

}


