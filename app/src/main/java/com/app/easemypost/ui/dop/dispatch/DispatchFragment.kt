package com.app.easemypost.ui.dop.dispatch

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentDispatchBinding
import com.app.easemypost.ui.dop.viewmodel.DopViewModel

class DispatchFragment : Fragment() {
    private lateinit var binding: FragmentDispatchBinding
    private val dopViewModel by activityViewModels<DopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDispatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val qrCodeBase64 = dopViewModel.qrCode
        if(!qrCodeBase64.isNullOrEmpty()) {
            val base64Image = qrCodeBase64.substringAfter("base64,")
            Log.d("ScheduleDelivery", dopViewModel.qrCode)
            // Decode base64 to a bitmap
            val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
            val decodedBitmap: Bitmap =
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Set the bitmap to the ImageView
            binding.qrCodeImageView.setImageBitmap(decodedBitmap)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.dashboard, false)
                }
            }
        )

    }

}