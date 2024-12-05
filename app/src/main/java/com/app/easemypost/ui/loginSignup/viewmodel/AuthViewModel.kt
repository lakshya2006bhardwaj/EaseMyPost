package com.app.easemypost.ui.loginSignup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.AdminLoginReq
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.response.AdminLoginRes
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

    private val _adminLoginDetails = MutableLiveData<ApiHandler<AdminLoginRes>>()
    val adminLoginDetails: LiveData<ApiHandler<AdminLoginRes>> = _adminLoginDetails

    fun adminSignUp(adminSignUpData: AdminSignUpReq) {
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

    fun adminLogin(adminLoginReq: AdminLoginReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _adminLoginDetails.postValue(ApiHandler.Loading)
                when (val result = authRepository.adminLogin(adminLoginReq = adminLoginReq)) {
                    is ApiHandler.Success -> {
                        _adminLoginDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _adminLoginDetails.postValue(result)
                    }

                    is ApiHandler.Loading -> {
                        _adminLoginDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
    fun clearData(){
        _adminLoginDetails.value = null
        _adminSignUpDetails.value = null
    }
}