package com.app.easemypost.domain.model.response

data class TruckDetailsRes(
    val truck: GetTrucksRes,
    val driver: DriverRes,
    val parcels: ArrayList<ParcelRes>
)
