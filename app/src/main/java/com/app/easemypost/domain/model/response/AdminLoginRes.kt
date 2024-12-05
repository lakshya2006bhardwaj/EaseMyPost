package com.app.easemypost.domain.model.response

data class AdminLoginRes(
    val message: String,
    val token: String,
    val admin: Admin
)
data class Admin(
    val _id: String,
    val stationName: String,
    val stationCode: String,
    val address: String,
    val pinCode: String,
    val adminName: String,
    val email: String,
    val phone: String,
    val agreementConfirmation: Boolean,
    val isVerified: Boolean,
    val otp: String?,
    val otpExpiry: String?,
    val __v: Int
)
