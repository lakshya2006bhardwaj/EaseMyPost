package com.app.easemypost.ui.dop.dashboard

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentScheduleDeliveryBinding
import com.app.easemypost.domain.model.requests.ParcelInfo
import com.app.easemypost.ui.dop.viewmodel.DopViewModel
import com.app.easemypost.ui.map.MapActivity
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ScheduleDeliveryFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDeliveryBinding
    val REQUEST_CODE_PICK_LOCATION = 100
    private val PERMISSION_REQUEST_CODE = 100
    private var pickupHour: Int = -1
    private var pickupMinute: Int = -1
    private val dopViewModel by activityViewModels<DopViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDeliveryBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAndRequestLocationPermission()
        init()
    }

    private fun init() {
        initSetViews()
        initClickListener()
    }

    private fun initSetViews() {

    }

    private fun initClickListener() = binding.apply {
        etPickupLoc.setOnClickListener {
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PICK_LOCATION)
        }

        etDestinationLoc.setOnClickListener {
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PICK_LOCATION)
        }

        etDeliveryDropDate.setOnClickListener {
            openCalender(etDeliveryDropDate)
        }
        etPickupDate.setOnClickListener {
            openCalender(etPickupDate)
        }
        etPickupTime.setOnClickListener { showTimePicker(true) }
        etDeliveryDropTime.setOnClickListener { showTimePicker(false) }
        btnNext.setOnClickListener {
            if (!etPickupLoc.text.isNullOrEmpty()) {
                if (!etDestinationLoc.text.isNullOrEmpty()) {
                    if (!etDeliveryDropDate.text.isNullOrEmpty()) {
                        if (!etPickupDate.text.isNullOrEmpty()) {
                            if (!etEntitiesNum.text.isNullOrEmpty()) {
                                if (!etParcelWeight.text.isNullOrEmpty()) {
                                    if (!etParcelLength.text.isNullOrEmpty()) {
                                        if (!etParcelWeight.text.isNullOrEmpty()) {
                                            if (!etParcelHeight.text.isNullOrEmpty()) {
                                                if (!etDeliveryDropTime.text.isNullOrEmpty()) {
                                                    if (!etPickupTime.text.isNullOrEmpty()) {
                                                        dopViewModel.scheduleDeliveryData = ParcelInfo(
                                                            parcelId = "${etEntitiesNum.text}${etParcelHeight.text}${etParcelLength.text}${etParcelWeight.text}",
                                                            dropDate = etDeliveryDropDate.text.toString(),
                                                            dropTime = etDeliveryDropTime.text.toString(),
                                                            pickUpDate = etPickupDate.text.toString(),
                                                            pickUpTime = etPickupTime.text.toString(),
                                                            dropOffLocation = etDestinationLoc.text.toString(),
                                                            pickupLocation = etPickupLoc.text.toString(),
                                                            numberOfEntities = etEntitiesNum.text.toString().toInt(),
                                                            height = etParcelHeight.text.toString(),
                                                            length = etParcelLength.text.toString(),
                                                            weight = etParcelWeight.text.toString().toDouble(),
                                                            breadth = etParcelBreadth.text.toString()
                                                        )
                                                        findNavController().navigate(R.id.action_scheduleDeliveryFragment_to_scheduleDeliveryNextPageFragment)
                                                    }
                                                    else{
                                                        etPickupTime.error = "Please select pickup time"
                                                    }
                                                }
                                                else{
                                                    etDeliveryDropTime.error = "Please select drop time"
                                                }
                                            }
                                            else{
                                                etParcelHeight.error = "Please enter height"
                                            }
                                        }
                                        else{
                                            etParcelWeight.error = "Please enter weight"
                                        }
                                    }
                                    else{
                                        etParcelLength.error = "Please enter length"
                                    }
                                }
                                else{
                                    etParcelWeight.error = "Please enter weight"
                                }
                            }
                            else{
                                etEntitiesNum.error = "Please enter number of entities"
                            }
                        }
                        else{
                            etPickupDate.error = "Please select pickup date"
                        }
                    }
                    else{
                        etDeliveryDropDate.error = "Please select drop date"
                    }
                }
                else{
                    etDestinationLoc.error = "Please select destination location"
                }
            }
            else{
                etPickupLoc.error = "Please select pickup location"
            }
        }


    }

    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, proceed with location-related task
            onLocationPermissionGranted()
        }
    }

    private fun onLocationPermissionGranted() {
        // Perform location-related task here
        Toast.makeText(requireContext(), "Location permission granted!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLocationPermissionGranted()
            } else {
                showPermissionExplanation()
                Toast.makeText(
                    requireContext(),
                    "Location permission is required.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Required")
            .setMessage("This app needs location access to provide location-based features.")
            .setPositiveButton("Grant") { _, _ ->
                checkAndRequestLocationPermission()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_LOCATION && resultCode == Activity.RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0)
            val longitude = data?.getDoubleExtra("longitude", 0.0)

            if (latitude != null && longitude != null) {
                val locationName = getAddressFromLatLng(latitude, longitude)

                if (binding.etPickupLoc.hasFocus()) {
                    binding.etPickupLoc.setText(locationName)
                } else if (binding.etDestinationLoc.hasFocus()) {
                    binding.etDestinationLoc.setText(locationName)
                }
            }
        }
    }

    private fun getAddressFromLatLng(latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                address.getAddressLine(0) ?: "Unknown Location"
            } else {
                "Unknown Location"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Unable to fetch address"
        }
    }

    private fun openCalender(etDate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog with a date restriction
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date and set it in the EditText
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                etDate.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )

        // Set the minimum date to today
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showTimePicker(isPickup: Boolean) = binding.apply {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (isPickup) {
                    pickupHour = selectedHour
                    pickupMinute = selectedMinute
                    etPickupTime.setText(time)
                    etDeliveryDropTime.text.clear() // Clear drop time if pickup time changes
                } else {
                    if (isDropTimeValid(selectedHour, selectedMinute)) {
                        etDeliveryDropTime.setText(time)
                    } else {
                        etDeliveryDropTime.error = "Drop time must be after pickup time"
                    }
                }
            },
            hour,
            minute,
            true
        )

        if (!isPickup && pickupHour != -1) {
            // Set minimum time for drop time to pickup time
            timePickerDialog.updateTime(pickupHour, pickupMinute)
        }

        timePickerDialog.show()
    }

    private fun isDropTimeValid(selectedHour: Int, selectedMinute: Int): Boolean {
        return (selectedHour > pickupHour) || (selectedHour == pickupHour && selectedMinute > pickupMinute)
    }


}