package com.app.easemypost.ui.loginSignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentFleetSignUpBinding

class FleetSignUpFragment : Fragment() {
    private lateinit var binding: FragmentFleetSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFleetSignUpBinding.inflate(inflater)
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

    private fun initSetViews() {
        // Placeholder for setting default views if required
    }

    private fun initClickListeners() {

    }

}
