package com.app.easemypost.ui.dop.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentTrackingBinding
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
    private val driverId = "truck_1" // The driver you want to track
    private lateinit var mapView: MapView
    private var driverMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure OSM
        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osm_prefs", 0)
        )

        // Initialize Firebase reference to the driver's location
        database = FirebaseDatabase.getInstance().getReference("drivers").child(driverId)

        // Set up the OSM map
        mapView = binding.map
        mapView.setMultiTouchControls(true)

        mapView.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);



        mapView.controller.setZoom(16.0)



        listenToDriverLocation()
    }

    private fun listenToDriverLocation() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val latitude = snapshot.child("latitude").getValue(Double::class.java) ?: return
                val longitude = snapshot.child("longitude").getValue(Double::class.java) ?: return

                // Update the marker with the new location
                updateDriverMarker(GeoPoint(latitude, longitude))
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle Firebase error (if any)
            }
        })
    }

    private fun updateDriverMarker(location: GeoPoint) {
        if (driverMarker == null) {
            // Create a new marker if it doesn't exist
            driverMarker = Marker(mapView).apply {
                position = location
                title = "Driver Location"
            }
            mapView.overlays.add(driverMarker)
        } else {
            // Update the existing marker position
            driverMarker?.position = location
        }
        // Move the camera to the new location
        mapView.controller.setCenter(location)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
