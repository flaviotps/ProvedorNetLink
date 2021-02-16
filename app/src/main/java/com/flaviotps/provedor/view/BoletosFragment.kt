package com.flaviotps.provedor.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.BoletoAdapter
import com.flaviotps.provedor.adapter.OnBoletoListener
import com.flaviotps.provedor.data.BoletoInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BoletosFragment(private var boletos: MutableList<BoletoInfo>, private val onBoletoListener: OnBoletoListener) : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_boletos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerOpen)
        context?.let {
            BoletoAdapter(boletos, it, onBoletoListener).apply {
                recyclerView.adapter = this
                notifyDataSetChanged()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}