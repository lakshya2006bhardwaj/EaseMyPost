package com.app.easemypost.domain.model.response

data class ParcelRes(
    val id: String,
    val weight: Double,
    val length: Double,
    val breadth: Double,
    val height: Double,
    val description: String,
    val pickUpLocation: String,
    val dropOffLocation: String,
    val status: String,
    val checkInTime: String,
    val checkOutTime: String
)


