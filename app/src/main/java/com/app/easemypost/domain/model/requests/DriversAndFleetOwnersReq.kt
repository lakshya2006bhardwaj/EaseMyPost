package com.app.easemypost.domain.model.requests

data class DriversAndFleetOwnersReq(
    val baseLocation:String,
    val requiredCapacity:String,
    val owner:String
)
