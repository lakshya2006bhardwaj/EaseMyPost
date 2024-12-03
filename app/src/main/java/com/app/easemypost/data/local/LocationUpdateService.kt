package com.app.easemypost.data.local

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.resources.R
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationCallback
import com.google.firebase.database.FirebaseDatabase

class LocationUpdateService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val database = FirebaseDatabase.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val driverId = intent?.getStringExtra("driverId") ?: return START_NOT_STICKY
        // Start foreground service
        val notification = Notification.Builder(this, "location_channel")
            .setContentTitle("Tracking Driver Location")
            .setContentText("Location updates running")
            .setSmallIcon(R.drawable.abc_vector_test)
            .build()

        startForeground(1, notification)
        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Create location request
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

        // Check if driver exists and proceed
        checkAndAddOrUpdateDriver(driverId)

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun checkAndAddOrUpdateDriver(driverId: String) {
        val driverRef = database.getReference("drivers").child(driverId)

        driverRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                // Driver does not exist, add a new entry
                val newDriverData = mapOf(
                    "name" to "Driver $driverId", // Add more details if needed
                    "latitude" to 0.0,
                    "longitude" to 0.0
                )
                driverRef.setValue(newDriverData)
                    .addOnSuccessListener { Log.d("Firebase", "Driver added: $driverId") }
                    .addOnFailureListener { e -> Log.e("Firebase", "Error adding driver: ${e.message}") }
            } else {
                // Driver exists, update location
                Log.d("Firebase", "Driver exists: $driverId")
            }
            // Start updating the driver's location
            startLocationUpdates(driverId)
        }.addOnFailureListener { e ->
            Log.e("Firebase", "Error checking driver: ${e.message}")
        }
    }

    private fun startLocationUpdates(driverId: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("LocationUpdateService", "Permission not granted")
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        updateDriverLocation(driverId, location.latitude, location.longitude)
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun updateDriverLocation(driverId: String, lat: Double, lng: Double) {
        val locationData = mapOf(
            "latitude" to lat,
            "longitude" to lng
        )
        database.getReference("drivers").child(driverId).updateChildren(locationData)
            .addOnSuccessListener { Log.d("Firebase", "Location updated for driver: $driverId") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error updating location: ${e.message}") }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "location_channel",
                "Driver Location Tracking",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }


    override fun onBind(intent: Intent?): IBinder? = null
}

