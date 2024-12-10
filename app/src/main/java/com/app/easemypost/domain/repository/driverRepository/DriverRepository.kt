package com.app.easemypost.domain.repository.driverRepository

import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.data.api.ApiInterface
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import javax.inject.Inject

class DriverRepository  @Inject constructor(
    private val apiInterface: ApiInterface
){
    suspend fun checkInParcel(params: CheckInReq): ApiHandler<CheckInRes> {
        return ApiHandler.handleApiCall{
            apiInterface.checkInParcel(request = params)
        }
    }

}