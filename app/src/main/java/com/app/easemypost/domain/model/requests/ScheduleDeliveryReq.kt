package com.app.easemypost.domain.model.requests

import java.math.BigDecimal
import java.util.Date

data class ScheduleDeliveryReq(
    val driverId: String,
    val parcelInfo: ParcelInfo,
)
data class ParcelInfo(
    val parcelId: String,
    val weight: Double,
    val length: String,
    val breadth: String,
    val height: String,
    val numberOfEntities: Int,
    val description: String? = null,
    val pickUpDate: String,
    val dropDate: String,
    val pickUpTime: String,
    val dropTime: String,
    val pickupLocation: String,
    val dropOffLocation: String,
    var status: ParcelStatus = ParcelStatus.Pending,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)

enum class ParcelStatus {
    Pending,
    In_Transit,
    Delivered
}

