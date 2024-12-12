package com.app.easemypost.domain.model.response

data class GetReceivingParcelRes(
    val parcelId: String,
    val driverId: String,
    val parcel:Parcel,
    val qrCode:String
)
