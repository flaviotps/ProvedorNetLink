package com.flaviotps.provedor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.AppTicket
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class TicketAdapter(private val tickets: MutableList<AppTicket>,
                    private val context: Context,
                    private val onTicketListener: OnTicketListener
) : RecyclerView.Adapter<TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view, onTicketListener)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
       val ticket = tickets[position]
       holder.setData(ticket)
    }

    override fun getItemCount(): Int {
       return tickets.size
    }
}

interface OnTicketListener {
    fun onClick(ticket: AppTicket)
}


class TicketViewHolder(itemView: View, private var onTicketListener: OnTicketListener) : RecyclerView.ViewHolder(itemView) {
    private val payButton: Button = itemView.findViewById(R.id.pay)
    private val dueDate: TextView = itemView.findViewById(R.id.duedate)
    private val value: TextView = itemView.findViewById(R.id.value)
    fun setData(ticket: AppTicket){


        ticket.dueDate?.let {
            it.split(" ").let { list ->
                if(!list.isNullOrEmpty()){
                    if(list[0].isNotEmpty()){
                        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                         inputFormat.parse(list[0])?.let { date ->
                            val outputDateStr = outputFormat.format(date)
                             dueDate.text = outputDateStr
                        }
                    }
                }
            }
        }

        value.text = "R$${ticket.value}"
        ticket.id?.let {
            payButton.setOnClickListener {
                onTicketListener.onClick(ticket)
            }
        }
    }
}
