package com.flaviotps.provedor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.BoletoInfo


class BoletoAdapter(private val boletos: MutableList<BoletoInfo>,
                    private val context: Context,
                    private val onBoletoListener: OnBoletoListener
) : RecyclerView.Adapter<BoletoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoletoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_boleto, parent, false)
        return BoletoViewHolder(view, onBoletoListener)
    }

    override fun onBindViewHolder(holder: BoletoViewHolder, position: Int) {
       val boleto = boletos[position]
       holder.setData(boleto)
    }

    override fun getItemCount(): Int {
       return boletos.size
    }
}

interface OnBoletoListener {
    fun onClick(boleto: BoletoInfo)
}


class BoletoViewHolder(itemView: View, private var onBoletoListener: OnBoletoListener) : RecyclerView.ViewHolder(itemView) {
    private val payButton: Button = itemView.findViewById(R.id.pay)
    private val dueDate: TextView = itemView.findViewById(R.id.duedate)
    private val value: TextView = itemView.findViewById(R.id.value)
    fun setData(boleto: BoletoInfo){
        dueDate.text = boleto.dueDate
        value.text = boleto.value
        payButton.setOnClickListener {
            onBoletoListener.onClick(boleto)
        }
    }
}
