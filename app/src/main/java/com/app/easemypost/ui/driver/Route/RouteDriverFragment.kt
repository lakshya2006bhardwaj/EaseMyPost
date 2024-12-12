package com.app.easemypost.ui.driver.Route

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.databinding.FragmentRouteDriverBinding
import com.app.easemypost.domain.model.requests.DriverLoginReq
import com.app.easemypost.domain.model.requests.OptimiseRouteReq
import com.app.easemypost.domain.model.requests.ParcelRequest
import com.app.easemypost.ui.driver.viewmodel.DriverViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import java.io.IOException
import java.util.Locale

class RouteDriverFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentRouteDriverBinding
    private var googleApiKey = ""
    private val driverViewModel by activityViewModels<DriverViewModel>()
    private var currentPolyline: Polyline? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteDriverBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleApiKey = getString(R.string.google_api_key)
        init()
    }

    private fun init() {
        initSetViews()
        initClickListner()
        getAllParcelsObserver()
        optimiseRouteResponseObserver()
    }

    private fun initSetViews() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun initClickListner() {

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.d("MapReady", "Google Map initialized")
        mMap.isMyLocationEnabled = true

        getOptimizedRouteFromBackend()

    }

    private fun getOptimizedRouteFromBackend() {
        driverViewModel.driverData?.phone?.let { driverViewModel.getParcelDetails(it) }
    }

    private fun drawRoute(route: List<LatLng>) {
        if (!::mMap.isInitialized) {
            Log.e("DrawRoute", "Map not initialized")
            return
        }
        if (route.size < 2) {
            Log.e("DrawRoute", "Not enough points to draw a route")
            return
        }
        mMap.clear() // Clear existing map markers and polylines

        // Draw the polyline
        val polylineOptions = PolylineOptions()
            .addAll(route)
            .color(Color.BLUE)
            .width(8f)
        mMap.addPolyline(polylineOptions)

        // Add start marker (blue antenna icon)
        val startMarker = route.first()
        mMap.addMarker(
            MarkerOptions()
                .position(startMarker)
                .title("Start")
        )

        // Add end marker (red antenna icon)
        val endMarker = route.last()
        mMap.addMarker(
            MarkerOptions()
                .position(endMarker)
                .title("End")

        )

        // Move camera to fit the route
        moveCameraToRoute(route)

        Log.d("DrawRoute", "Route drawn with ${route.size} points")
    }
    private fun bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    private fun moveCameraToRoute(route: List<LatLng>) {
        val boundsBuilder = LatLngBounds.Builder()
        route.forEach { boundsBuilder.include(it) }
        val bounds = boundsBuilder.build()
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    @SuppressLint("MissingPermission")
    private fun trackDriverLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // Send current location to backend to check if driver is off-route
                sendLocationToBackend(it.latitude, it.longitude)
            }
        }
    }

    private fun sendLocationToBackend(latitude: Double, longitude: Double) {
        // Implement your API call to send location to backend for off-route check
        Log.d("MapFragment", "Location sent to backend: $latitude, $longitude")
    }

    private fun optimiseRouteResponseObserver() {
        driverViewModel.optimizeRouteData.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                    val listRoute = arrayListOf<LatLng>()

                    // Populate listRoute with LatLng points
                    for (route in res.data.route) {
                        val startLatLng =
                            LatLng(route.startLocation.latitude, route.startLocation.longitude)
                        val endLatLng =
                            LatLng(route.endLocation.latitude, route.endLocation.longitude)
                        listRoute.add(startLatLng)
                        listRoute.add(endLatLng)
                    }

                    // Draw route on the map
                    drawRoute(listRoute)

                    // Track driver's location
                    trackDriverLocation()
                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
                        res.exception.message ?: "An error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("ScheduleDelivery", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("ScheduleDelivery", "Loading")
                }
            }
        }
    }


    private fun getAllParcelsObserver() {
        driverViewModel.parcelDetails.observe(viewLifecycleOwner) { res ->
            when (res) {
                is ApiHandler.Success -> {
                    Log.d("ScheduleDelivery", res.data.toString())
                    var parcels = arrayListOf<ParcelRequest>()
                    for (parcel in res.data.parcels) {
                        // Convert pickup and drop-off locations to LatLng using Geocoder
                        val pickupLatLng = getLatLngFromAddress(parcel.pickupLocation)
                        val dropoffLatLng = getLatLngFromAddress(parcel.dropOffLocation)

                        if (pickupLatLng != null && dropoffLatLng != null) {
                            parcels.add(
                                ParcelRequest(
                                    dropoffLocation = "${dropoffLatLng.latitude},${dropoffLatLng.longitude}",
                                    pickupLocation = "${pickupLatLng.latitude},${pickupLatLng.longitude}"
                                )
                            )
                        } else {
                            Log.e("ScheduleDelivery", "Unable to get lat/lng for ${parcel}")
                        }
                    }
                    driverViewModel.routeOptimise(
                        OptimiseRouteReq(
                            driverId = res.data.driver.driverPhone,
                            parcels = parcels
                        )
                    )

                }

                is ApiHandler.Error -> {
                    Toast.makeText(
                        requireContext(),
                        res.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    res.errorMessage?.let { Log.d("ScheduleDelivery", it) }
                }

                is ApiHandler.Loading -> {
                    Log.d("ScheduleDelivery", "Loading")
                }
            }
        }
    }

    private fun getLatLngFromAddress(address: String): LatLng? {
        return try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val location = addresses[0]
                LatLng(location.latitude, location.longitude)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        driverViewModel.clearData()
    }

}



