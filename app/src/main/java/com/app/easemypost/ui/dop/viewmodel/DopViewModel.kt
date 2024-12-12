package com.app.easemypost.ui.dop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.GetReceivingParcelReq
import com.app.easemypost.domain.model.requests.ParcelInfo
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.domain.model.response.AdminSignUpRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GetReceivingParcelRes
import com.app.easemypost.domain.model.response.GetTrucksRes
import com.app.easemypost.domain.model.response.ScheduleDeliveryRes
import com.app.easemypost.domain.model.response.TruckDetailsRes
import com.app.easemypost.domain.repository.dopRepo.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DopViewModel @Inject constructor(private val dashboardRepository: DashboardRepository) : ViewModel() {

    var scheduleDeliveryData:ParcelInfo? = null
    var qrCode = ""
    var truckDetailsData:TruckDetailsRes?=null
    var receiveParcelData = arrayListOf<GetReceivingParcelRes>()

    private val _driverAndFleetOwnersDetails = MutableLiveData<ApiHandler<DriversAndFleetOwnersRes>>()
    val driverAndFleetOwnersDetails: LiveData<ApiHandler<DriversAndFleetOwnersRes>> = _driverAndFleetOwnersDetails

    private val _scheduleDeliveryData = MutableLiveData<ApiHandler<ScheduleDeliveryRes>>()
    val scheduleDelivery: LiveData<ApiHandler<ScheduleDeliveryRes>> = _scheduleDeliveryData

    private val _allTrucks = MutableLiveData<ApiHandler<ArrayList<GetTrucksRes>>>()
    val allTrucks: LiveData<ApiHandler<ArrayList<GetTrucksRes>>> = _allTrucks

    private val _truckDetails = MutableLiveData<ApiHandler<TruckDetailsRes>>()
    val truckDetails: LiveData<ApiHandler<TruckDetailsRes>> = _truckDetails

    private val _receiveParcel = MutableLiveData<ApiHandler<ArrayList<GetReceivingParcelRes>>>()
    val receiveParcel: LiveData<ApiHandler<ArrayList<GetReceivingParcelRes>>> = _receiveParcel
    fun getDriverAndFleetOwners(params:DriversAndFleetOwnersReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _driverAndFleetOwnersDetails.postValue(ApiHandler.Loading)
                when (val result = dashboardRepository.getDriverAndFleetOwners(params = params)) {
                    is ApiHandler.Success -> {
                        _driverAndFleetOwnersDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _driverAndFleetOwnersDetails.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _driverAndFleetOwnersDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }

    fun scheduleDelivery(params:ScheduleDeliveryReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _scheduleDeliveryData.postValue(ApiHandler.Loading)
                when (val result = dashboardRepository.scheduleDelivery(params = params)) {
                    is ApiHandler.Success -> {
                        _scheduleDeliveryData.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _scheduleDeliveryData.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _scheduleDeliveryData.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }

    fun getAllTrucks(adminID:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _allTrucks.postValue(ApiHandler.Loading)
                when (val result = dashboardRepository.getAllTrucks(adminID = adminID)) {
                    is ApiHandler.Success -> {
                        _allTrucks.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _allTrucks.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _allTrucks.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }

    fun getTruckDetails(adminID:String , truckID:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _truckDetails.postValue(ApiHandler.Loading)
                when (val result = dashboardRepository.getTruckDetail(adminID = adminID , truckID = truckID)) {
                    is ApiHandler.Success -> {
                        truckDetailsData = result.data
                        _truckDetails.postValue(result)
                    }

                    is ApiHandler.Error -> {
                        _truckDetails.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _truckDetails.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }
    fun getReceiveParcel(param:GetReceivingParcelReq){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _receiveParcel.postValue(ApiHandler.Loading)
                when (val result = dashboardRepository.getReceiveParcel(param = param)) {
                    is ApiHandler.Success -> {
                        _receiveParcel.postValue(result)
                        for(i in result.data.indices){
                            receiveParcelData.add(result.data[i])
                        }
                    }

                    is ApiHandler.Error -> {
                        _receiveParcel.postValue(result)
                    }

                    is ApiHandler.Loading-> {
                        _receiveParcel.postValue(ApiHandler.Loading)
                    }
                }
            }
        }
    }

    fun clearData(){
        _driverAndFleetOwnersDetails.value = null
        _scheduleDeliveryData.value = null
    }

}