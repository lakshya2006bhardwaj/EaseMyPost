package com.app.easemypost.data.api

import com.app.easemypost.common.ApiConstant
import com.app.easemypost.domain.model.requests.AdminLoginReq
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.requests.AdminVerifyOtpReq
import com.app.easemypost.domain.model.requests.CheckInReq
import com.app.easemypost.domain.model.requests.DriverLoginReq
import com.app.easemypost.domain.model.requests.DriversAndFleetOwnersReq
import com.app.easemypost.domain.model.requests.GetReceivingParcelReq
import com.app.easemypost.domain.model.requests.OptimiseRouteReq
import com.app.easemypost.domain.model.requests.ScheduleDeliveryReq
import com.app.easemypost.domain.model.response.AdminLoginRes
import com.app.easemypost.domain.model.response.AdminSignUpRes
import com.app.easemypost.domain.model.response.CheckInRes
import com.app.easemypost.domain.model.response.DirectionsResponse
import com.app.easemypost.domain.model.response.DriverLoginRes
import com.app.easemypost.domain.model.response.DriversAndFleetOwnersRes
import com.app.easemypost.domain.model.response.GeocodeResponse
import com.app.easemypost.domain.model.response.GetParcelRes
import com.app.easemypost.domain.model.response.GetReceivingParcelRes
import com.app.easemypost.domain.model.response.GetTrucksRes
import com.app.easemypost.domain.model.response.RouteOptimiseRes
import com.app.easemypost.domain.model.response.ScheduleDeliveryRes
import com.app.easemypost.domain.model.response.TruckDetailsRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST(ApiConstant.ROUTE_OPTIMISE)
    suspend fun routeOptimise(
        @Body params: OptimiseRouteReq
    ):Response<RouteOptimiseRes>

    @POST(ApiConstant.DRIVER_LOGIN)
    suspend fun loginDriver(@Body request: DriverLoginReq): Response<DriverLoginRes>

    @GET(ApiConstant.GET_PARCELS+"{driverId}")
    suspend fun getParcels(
        @Path("driverId") driverId: String
    ): Response<GetParcelRes>

    @POST(ApiConstant.RECEIVING_PARCEL)
    suspend fun getReceivingParcel(
        @Body params: GetReceivingParcelReq
    ): Response<ArrayList<GetReceivingParcelRes>>
}