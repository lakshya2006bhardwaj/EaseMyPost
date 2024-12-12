package com.app.easemypost.domain.model.response

data class GeocodeResponse(
val lat: String,
val lon: String
)

// Directions response
data class DirectionsResponse(
    val status: String,
    val routes: List<Route>
)

data class Route(
    val legs: List<Leg>
)

data class Leg(
    val start_address: String,
    val end_address: String,
    val duration: Duration,
    val distance: Distance
)

data class Duration(val text: String)
data class Distance(val text: String)


