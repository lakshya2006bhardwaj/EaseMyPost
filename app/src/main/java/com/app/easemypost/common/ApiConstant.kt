package com.app.easemypost.common

object ApiConstant {
    const val BASE_URL = "http://192.168.1.2:5001/api/"

    //ADMIN
    const val ADMIN_SIGNUP = "admin/auth/signup"
    const val ADMIN_LOGIN = "admin/auth/login"
    const val ADMIN_VERIFY_OTP = "admin/auth/verify-otp"
    const val DRIVERS_AND_FLEET_OWNERS = "driver/assign"
    const val SCHEDULE_DELIVERY = "admin/schedule/delivery"
    const val GET_ALL_TRUCKS = "admin/trucks/"
    const val GET_PARCEL_DETAILS = "admin/truck/"

    //DRIVER
    const val CHECK_IN_PARCEL = "admin/checkin"

}