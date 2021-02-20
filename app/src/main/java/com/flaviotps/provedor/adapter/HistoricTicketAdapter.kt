package com.flaviotps.provedor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.TicketInfo
import com.flaviotps.provedor.extensions.show


class HistoricTicketAdapter(private val tickets: MutableList<TicketInfo>,
                    private val context: Context,
                    private val onOnHistoricTickerListener: OnHistoricTicketListener
) : RecyclerView.Adapter<HistoricTicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricTicketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ticket_historic, parent, false)
        return HistoricTicketViewHolder(view, onOnHistoricTickerListener)
    }

    override fun onBindViewHolder(holder: HistoricTicketViewHolder, position: Int) {
       val ticket = tickets[position]
       holder.setData(ticket)
    }

    override fun getItemCount(): Int {
       return tickets.size
    }
}

interface OnHistoricTicketListener {
    fun onClick(ticket: TicketInfo)
}


class HistoricTicketViewHolder(itemView: View, private var onOnHistoricTickerListener: OnHistoricTicketListener) : RecyclerView.ViewHolder(itemView) {
    private val receiptButton: Button = itemView.findViewById(R.id.receipt)
    private val dueDate: TextView = itemView.findViewById(R.id.duedate)
    private val value: TextView = itemView.findViewById(R.id.value)
    fun setData(ticket: TicketInfo){
        dueDate.text = ticket.dueDate
        ticket.value?.let { value.text = it }
        ticket.dueDate?.let { dueDate.text = it }
        ticket.link?.let {
            receiptButton.show()
            receiptButton.setOnClickListener {
                    onOnHistoricTickerListener.onClick(ticket)
            }
        }
    }
}
