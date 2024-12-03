package com.app.easemypost.ui.loginSignup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.response.AdminSignUpRes
import com.app.easemypost.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _adminSignUpDetails = MutableLiveData<ApiHandler<AdminSignUpRes>>()
    val adminSignUpDetails: LiveData<ApiHandler<AdminSignUpRes>> = _adminSignUpDetails

    fun getRechargePlansDetails(adminSignUpData: AdminSignUpReq) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _adminSignUpDetails.postValue(ApiHandler.Loading)
                when (val result = authRepository.adminSignUp(adminSignupReq = adminSignUpData)) {
                    is ApiHandler.Success -> {
                        _adminSignUpDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _adminSignUpDetails.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _adminSignUpDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
}