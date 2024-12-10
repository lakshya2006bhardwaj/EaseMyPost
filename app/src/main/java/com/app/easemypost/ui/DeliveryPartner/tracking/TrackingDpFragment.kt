package com.app.easemypost.ui.DeliveryPartner.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentTrackingDpBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TrackingDpFragment : Fragment() {

    private lateinit var binding: FragmentTrackingDpBinding
    private lateinit var database: DatabaseReference
    private val driverId = "truck_1"
    private lateinit var googleMap: GoogleMap
    private var driverMarker: Marker? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackingDpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase reference to the driver's location
        database = FirebaseDatabase.getInstance().getReference("drivers").child(driverId)

        // Set up Google Map
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback { map ->
            googleMap = map
            googleMap.uiSettings.isZoomControlsEnabled = true
            listenToDriverLocation()
        })
    }

    private fun listenToDriverLocation() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val latitude = snapshot.child("latitude").getValue(Double::class.java) ?: return
                val longitude = snapshot.child("longitude").getValue(Double::class.java) ?: return

                // Update the marker with the new location
                updateDriverMarker(LatLng(latitude, longitude))
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle Firebase error
            }
        })
    }

    private fun updateDriverMarker(location: LatLng) {
        if (driverMarker == null) {
            // Create a new marker if it doesn't exist
            driverMarker = googleMap.addMarker(
                MarkerOptions().position(location).title("Driver Location")
            )
        } else {
            // Update the existing marker position
            driverMarker?.position = location // Update marker position
        }
        // Move the camera to the new location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}