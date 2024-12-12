package com.app.easemypost.domain.model.response

data class DriverLoginRes(
    val message: String,
    val token: String,
    val driver: DriverDetails
)

data class DriverDetails(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val baseLocation: String,
    val licenseNumber: String,
)




