package com.app.easemypost.ui.dop.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.easemypost.R
import com.app.easemypost.databinding.FragmentReceivedDeliveriesBinding
import com.app.easemypost.ui.dop.dashboard.adapter.ReceivingDeliveryAdapter
import com.app.easemypost.ui.dop.viewmodel.DopViewModel

class ReceivedDeliveriesFragment : Fragment() {
    private lateinit var binding:FragmentReceivedDeliveriesBinding
    private var adapter:ReceivingDeliveryAdapter?=null
    private val dopViewModel by activityViewModels<DopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReceivedDeliveriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitViews()
    }
    private fun setInitViews()=binding.apply {
        adapter = ReceivingDeliveryAdapter{pos , data,src->
            onItemClick(pos,data,src)
        }
        rvReceivingDelivery.adapter = adapter
        adapter?.submitList(dopViewModel.receiveParcelData)

    }

    private fun onItemClick(pos: Int, data: Any, src: String) {

    }
}