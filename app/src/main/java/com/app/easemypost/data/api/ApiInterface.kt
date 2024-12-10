package com.app.easemypost.data.api

import com.app.easemypost.common.ApiConstant
import com.app.easemypost.domain.model.requests.AdminLoginReq
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.requests.AdminVerifyOtpReq
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.domain.model.response.AdminLoginRes
import com.app.easemypost.domain.model.response.AdminSignUpRes
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GetTrucksRes
import com.app.easemypost.domain.model.response.ScheduleDeliveryRes
import com.app.easemypost.domain.model.response.TruckDetailsRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @POST(ApiConstant.ADMIN_SIGNUP)
    suspend fun adminSignUp(
        @Body adminSignUpReq:AdminSignUpReq
    ):Response<AdminSignUpRes>

    @POST(ApiConstant.ADMIN_LOGIN)
    suspend fun adminLogin(
        @Body adminLoginReq:AdminLoginReq
    ):Response<AdminLoginRes>

    @POST(ApiConstant.ADMIN_VERIFY_OTP)
    suspend fun adminVerifyOtp(
        @Body adminVerifyOtpReq:AdminVerifyOtpReq
    ):Response<AdminLoginRes>

    @POST(ApiConstant.DRIVERS_AND_FLEET_OWNERS)
    suspend fun getDriversAndFleetOwners(
        @Body params:DriversAndFleetOwnersReq
    ):Response<DriversAndFleetOwnersRes>

    @POST(ApiConstant.SCHEDULE_DELIVERY)
    suspend fun scheduleDelivery(
        @Body params:ScheduleDeliveryReq
    ):Response<ScheduleDeliveryRes>
    @GET(ApiConstant.GET_ALL_TRUCKS+"{adminPhone}")
    suspend fun getAssignedTrucks(
        @Path("adminPhone") adminId: String
    ): Response<ArrayList<GetTrucksRes>>

    @GET(ApiConstant.GET_PARCEL_DETAILS+"{adminId}/{truckId}")
    suspend fun getTruckDetails(
        @Path("adminId") adminId: String, @Path("truckId") truckId: String
    ): Response<TruckDetailsRes>

    @POST(ApiConstant.CHECK_IN_PARCEL)
    suspend fun checkInParcel(
        @Body request: CheckInReq
    ): Response<CheckInRes>
}