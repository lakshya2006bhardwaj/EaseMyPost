package com.app.easemypost.domain.model.response

data class GetTrucksRes(
    val id: String,
    val truckId: String,
    val baseLocation: String,
    val capacity: Double,
    val availableCapacity: Double,
    val availability: Boolean,
    val owner: String,
    val isAssigned: Boolean,
    val createdAt: String,
    val updatedAt: String

)
