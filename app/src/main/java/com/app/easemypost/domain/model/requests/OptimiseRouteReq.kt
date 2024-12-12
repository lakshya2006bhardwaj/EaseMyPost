package com.app.easemypost.domain.model.requests

import com.app.easemypost.domain.model.response.Parcel

data class OptimiseRouteReq(
    val driverId:String,
    val parcels:ArrayList<ParcelRequest>
)
data class ParcelRequest(
    val pickupLocation: String,
    val dropoffLocation: String
)
