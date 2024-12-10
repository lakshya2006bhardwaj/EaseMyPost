package com.app.easemypost.ui.dop.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentTrackingBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding
    private lateinit var database: DatabaseReference
    private val driverId = "truck_1"
    private lateinit var googleMap: GoogleMap
    private var driverMarker: com.google.android.gms.maps.model.Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            driverMarker = googleMap.addMarker(
                MarkerOptions().position(location).title("Driver Location")
            )
        } else {
            driverMarker?.position = location
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}