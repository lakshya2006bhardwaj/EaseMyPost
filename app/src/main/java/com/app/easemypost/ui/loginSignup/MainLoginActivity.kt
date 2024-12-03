package com.app.easemypost.ui.loginSignup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.easemypost.R
import com.app.easemypost.databinding.ActivityMainLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainLoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainLoginBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController



    }
}