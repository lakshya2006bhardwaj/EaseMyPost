package com.app.easemypost.domain.model.response

import java.util.Date

data class GetParcelRes(
    val success: Boolean,
    val driver: Driver,
    val parcels: List<ParcelDetails>
)

data class Driver(
    val driverName: String,
    val driverPhone: String
)

data class ParcelDetails(
    val _id: String,
    val driverId: String,
    val weight: Double,
    val length: String,
    val breadth: String,
    val height: String,
    val numberOfEntities: Int,
    val description: String,
    val pickUpDate: String,
    val dropDate: String,
    val pickUpTime: String,
    val dropTime: String,
    val pickupLocation: String,
    val dropOffLocation: String,
    val status: String ,
    var qrCode: String?,
    val checkInTime: String?,
    val checkOutTime: String?,
    val updatedAt: String
)


