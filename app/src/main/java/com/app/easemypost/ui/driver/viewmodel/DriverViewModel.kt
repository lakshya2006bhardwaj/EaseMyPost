package com.app.easemypost.ui.driver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriverLoginReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.OptimiseRouteReq
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DriverDetails
import com.app.easemypost.domain.model.response.DriverLoginRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GetParcelRes
import com.app.easemypost.domain.model.response.RouteOptimiseRes
import com.app.easemypost.domain.repository.driverRepository.DriverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DriverViewModel@Inject constructor(private val driverRepository: DriverRepository) : ViewModel() {
    var driverData: DriverDetails?=null
    private val _checkInDetails = MutableLiveData<ApiHandler<CheckInRes>>()
    val checkInDetails: LiveData<ApiHandler<CheckInRes>> = _checkInDetails

    private val _driverLogin = MutableLiveData<ApiHandler<DriverLoginRes>>()
    val driverLogin: LiveData<ApiHandler<DriverLoginRes>> = _driverLogin

    private val _optimizeRouteData = MutableLiveData<ApiHandler<RouteOptimiseRes>>()
    val optimizeRouteData: LiveData<ApiHandler<RouteOptimiseRes>> = _optimizeRouteData

    private val _parcelDetails = MutableLiveData<ApiHandler<GetParcelRes>>()
    val parcelDetails: LiveData<ApiHandler<GetParcelRes>> = _parcelDetails



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

    fun routeOptimise(params: OptimiseRouteReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _optimizeRouteData.postValue(ApiHandler.Loading)
                when (val result = driverRepository.routeOptimise(params = params)) {
                    is ApiHandler.Success -> {
                        _optimizeRouteData.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _optimizeRouteData.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _optimizeRouteData.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
    fun driverLogin(params: DriverLoginReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _driverLogin.postValue(ApiHandler.Loading)
                when (val result = driverRepository.driverLogin(params = params)) {
                    is ApiHandler.Success -> {
                        driverData = result.data.driver
                        _driverLogin.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _driverLogin.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _driverLogin.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }

    fun getParcelDetails(params: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _parcelDetails.postValue(ApiHandler.Loading)
                when (val result = driverRepository.getAllParcels(params = params)) {
                    is ApiHandler.Success -> {
                        _parcelDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _parcelDetails.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _parcelDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
    fun clearData(){
        _parcelDetails.value = null
        _driverLogin.value = null
        _optimizeRouteData.value = null
    }
}