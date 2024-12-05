package com.app.easemypost.domain.model.requests

data class AdminVerifyOtpReq(
    val phone:String,
    val otp:String
)
