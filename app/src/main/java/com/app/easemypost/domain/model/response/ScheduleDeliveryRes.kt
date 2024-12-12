package com.app.easemypost.domain.model.response

import java.util.Date

data class ScheduleDeliveryRes(
    val message: String,
    val parcel: Parcel
)

data class Parcel(
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
    val status: String = "Pending",
    var qrCode: String?,
    val checkInTime: String,
    val checkOutTime:String,
    val updatedAt: Date?=null

)

