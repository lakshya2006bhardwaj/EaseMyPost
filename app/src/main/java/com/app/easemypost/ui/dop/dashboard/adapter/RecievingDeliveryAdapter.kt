package com.app.easemypost.ui.dop.dashboard.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.easemypost.databinding.ItemRecievingDeliveryBinding
import com.app.easemypost.domain.model.response.GetReceivingParcelRes

class ReceivingDeliveryAdapter(
    val onWaterTasteItemClick: (pos: Int, model: GetReceivingParcelRes, src: String) -> Unit
) : ListAdapter<GetReceivingParcelRes, ReceivingDeliveryAdapter.MyViewHolder>(WaterTestDesAdapterCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecievingDeliveryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    inner class MyViewHolder(private val binding: ItemRecievingDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun setData(data: GetReceivingParcelRes) {
            with(binding) {
                val qrCodeBase64 = data.qrCode
                if(!qrCodeBase64.isNullOrEmpty()) {
                    val base64Image = qrCodeBase64.substringAfter("base64,")
                    val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedBitmap: Bitmap =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                    ivCheckOutQR.setImageBitmap(decodedBitmap)
                }
                tvParcelIdData.text = data.parcelId
                tvAddressData.text = data.parcel.dropOffLocation
            }

        }
    }

    object WaterTestDesAdapterCallback : DiffUtil.ItemCallback<GetReceivingParcelRes>() {
        override fun areContentsTheSame(
            oldItem: GetReceivingParcelRes,
            newItem: GetReceivingParcelRes
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: GetReceivingParcelRes,
            newItem: GetReceivingParcelRes
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun submitList(list: List<GetReceivingParcelRes>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}