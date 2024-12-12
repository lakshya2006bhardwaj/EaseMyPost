package com.app.easemypost.domain.repository.dopRepo

import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.data.api.ApiInterface
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.GetReceivingParcelReq
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.domain.model.response.AdminSignUpRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GetReceivingParcelRes
import com.app.easemypost.domain.model.response.GetTrucksRes
import com.app.easemypost.domain.model.response.ScheduleDeliveryRes
import com.app.easemypost.domain.model.response.TruckDetailsRes
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiInterface: ApiInterface
){
    suspend fun getDriverAndFleetOwners(params: DriversAndFleetOwnersReq): ApiHandler<DriversAndFleetOwnersRes> {
        return ApiHandler.handleApiCall{
            apiInterface.getDriversAndFleetOwners(params = params)
        }
    }
    suspend fun scheduleDelivery(params: ScheduleDeliveryReq): ApiHandler<ScheduleDeliveryRes> {
        return ApiHandler.handleApiCall{
            apiInterface.scheduleDelivery(params = params)
        }
    }
    suspend fun getAllTrucks(adminID:String): ApiHandler<ArrayList<GetTrucksRes>> {
        return ApiHandler.handleApiCall{
            apiInterface.getAssignedTrucks(adminId = adminID)
        }
    }
    suspend fun getTruckDetail(adminID:String , truckID:String): ApiHandler<TruckDetailsRes> {
        return ApiHandler.handleApiCall{
            apiInterface.getTruckDetails(adminId = adminID , truckId = truckID)
        }
    }
    suspend fun getReceiveParcel(param:GetReceivingParcelReq): ApiHandler<ArrayList<GetReceivingParcelRes>> {
        return ApiHandler.handleApiCall{
            apiInterface.getReceivingParcel(param)
        }
    }
}