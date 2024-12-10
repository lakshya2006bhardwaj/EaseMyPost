package com.app.easemypost.ui.driver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.repository.driverRepository.DriverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DriverViewModel@Inject constructor(private val driverRepository: DriverRepository) : ViewModel() {
    private val _checkInDetails = MutableLiveData<ApiHandler<CheckInRes>>()
    val checkInDetails: LiveData<ApiHandler<CheckInRes>> = _checkInDetails
    fun checkInDetails(params: CheckInReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _checkInDetails.postValue(ApiHandler.Loading)
                when (val result = driverRepository.checkInParcel(params = params)) {
                    is ApiHandler.Success -> {
                        _checkInDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _checkInDetails.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _checkInDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
}