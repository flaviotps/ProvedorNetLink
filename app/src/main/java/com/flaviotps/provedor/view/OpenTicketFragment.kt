package com.flaviotps.provedor.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.adapter.OnTicketListener
import com.flaviotps.provedor.adapter.TicketAdapter
import com.flaviotps.provedor.data.AppTicket

class OpenTicketFragment(private var tickets: MutableList<AppTicket>, private val onTicketListener: OnTicketListener) : Fragment() {
    private lateinit var recyclerOpen: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerOpen = view.findViewById(R.id.recyclerOpen)
        context?.let {
            TicketAdapter(tickets, it, onTicketListener).apply {
                recyclerOpen.adapter = this
                notifyDataSetChanged()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}