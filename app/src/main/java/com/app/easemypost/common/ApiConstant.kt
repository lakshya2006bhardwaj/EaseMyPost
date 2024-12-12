package com.app.easemypost.common

object ApiConstant {
    const val BASE_URL = "http://10.10.56.27:5001/api/"

    //ADMIN
    const val ADMIN_SIGNUP = "admin/auth/signup"
    const val ADMIN_LOGIN = "admin/auth/login"
    const val ADMIN_VERIFY_OTP = "admin/auth/verify-otp"
    const val DRIVERS_AND_FLEET_OWNERS = "driver/assign"
    const val SCHEDULE_DELIVERY = "admin/schedule/delivery"
    const val GET_ALL_TRUCKS = "admin/trucks/"
    const val GET_PARCEL_DETAILS = "admin/truck/"
    const val RECEIVING_PARCEL = "admin/receiving-deliveries"

    //DRIVER
    const val CHECK_IN_PARCEL = "admin/checkin"

    //ROUTES
    const val ROUTE_OPTIMISE = "routeOptimization/assignRoute"
    const val DRIVER_LOGIN = "driver/auth/login"
    const val GET_PARCELS = "driver/parcels/"
    const val MAP_API = "https://maps.googleapis.com/"

}