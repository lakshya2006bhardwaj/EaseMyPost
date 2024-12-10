package com.app.easemypost.domain.model.response

data class DriverRes(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val licenseNumber: String,
    val baseLocation: String,
    val truckInfo: TruckInfo
)

data class TruckInfo(
    val truckId: String,
    val truckCapacity: Double
)


