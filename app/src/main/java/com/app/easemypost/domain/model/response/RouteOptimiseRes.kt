package com.app.easemypost.domain.model.response

data class RouteOptimiseRes(
    val success: Boolean,
    val route: List<RouteLeg>,
    val totalDistance: Double,
    val totalDuration: Double
)

data class RouteLeg(
    val startLocation: Location,
    val endLocation: Location,
    val duration: Double, // Duration in seconds
    val distance: Double  // Distance in meters
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

