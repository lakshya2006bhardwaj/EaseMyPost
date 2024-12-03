package com.app.easemypost.domain.model.requests

data class AdminSignUpReq(
    val stationName:String,
    val stationCode:String,
    val address:String,
    val pinCode:String,
    val adminName:String,
    val email:String,
    val phone:String,
    val agreementConfirmation:Boolean,
    val isVerified:Boolean,
    val otp:String,
    val otpExpiry:String
)
