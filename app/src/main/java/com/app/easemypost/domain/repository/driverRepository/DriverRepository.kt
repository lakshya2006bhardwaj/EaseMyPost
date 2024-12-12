package com.app.easemypost.domain.repository.driverRepository

import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.data.api.ApiInterface
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriverLoginReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.OptimiseRouteReq
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DriverLoginRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GetParcelRes
import com.app.easemypost.domain.model.response.RouteOptimiseRes
import javax.inject.Inject

class DriverRepository  @Inject constructor(
    private val apiInterface: ApiInterface
){
    suspend fun checkInParcel(params: CheckInReq): ApiHandler<CheckInRes> {
        return ApiHandler.handleApiCall{
            apiInterface.checkInParcel(request = params)
        }
    }

    suspend fun routeOptimise(params:OptimiseRouteReq): ApiHandler<RouteOptimiseRes> {
        return ApiHandler.handleApiCall{
            apiInterface.routeOptimise(params = params)
        }
    }

    suspend fun driverLogin(params:DriverLoginReq): ApiHandler<DriverLoginRes> {
        return ApiHandler.handleApiCall{
            apiInterface.loginDriver(request = params)
        }
    }

    suspend fun getAllParcels(params:String): ApiHandler<GetParcelRes> {
        return ApiHandler.handleApiCall{
            apiInterface.getParcels(driverId = params)
        }
    }


}