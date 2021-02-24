package com.flaviotps.provedor.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.adapter.HistoricTicketAdapter
import com.flaviotps.provedor.adapter.OnHistoricTicketListener
import com.flaviotps.provedor.data.AppTicket
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistoricTicketFragment (private var tickets: MutableList<AppTicket>, private val onHistoricTicketListener: OnHistoricTicketListener) : BottomSheetDialogFragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var closeButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_historic_tickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.recycler)
        closeButton = view.findViewById(R.id.close)
        closeButton.setOnClickListener { this.dismiss() }
        context?.let {
            HistoricTicketAdapter(tickets, it, onHistoricTicketListener).apply {
                recycler.adapter = this
                notifyDataSetChanged()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}