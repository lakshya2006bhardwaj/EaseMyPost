package com.app.easemypost.domain.model.response

data class DriversAndFleetOwnersRes(
    val success: Boolean,
    val drivers: List<AssignedData>? = null,
    val fleetOwners: List<String>? = null,
    val message: String? = null
)

data class AssignedData(
    val driverId: String,
    val driverName: String,
    val truckId: String,
    val truckCapacity: Int
)


