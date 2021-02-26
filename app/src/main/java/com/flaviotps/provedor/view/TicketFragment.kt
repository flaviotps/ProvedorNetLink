package com.flaviotps.provedor.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.flaviotps.provedor.EXTRA_KEY_TICKET_GWT
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.AppClient
import com.flaviotps.provedor.data.AppTicket
import com.flaviotps.provedor.data.TicketRequest
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TicketFragment(private val ticket: AppTicket)  : BottomSheetDialogFragment() {

    private lateinit var closeButton: ImageButton
    private lateinit var code: Button
    private lateinit var pdf: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeButton = view.findViewById(R.id.close)
        pdf = view.findViewById(R.id.pdf)
        code = view.findViewById(R.id.code)
        code.setOnClickListener {
            context?.let { context ->
                val clipboard: ClipboardManager? = getSystemService(context, ClipboardManager::class.java)
                val clip = ClipData.newPlainText("CÓDIGO BOLETO", ticket.code)
                clipboard?.setPrimaryClip(clip)
                Toast.makeText(context, "Código copiado", Toast.LENGTH_LONG).show()
            }
        }
        pdf.setOnClickListener {
            if(!ticket.gwtId.isNullOrEmpty()) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra(EXTRA_KEY_TICKET_GWT, ticket.gwtId)
                startActivity(intent)
                dismiss()
            }
        }
        closeButton.setOnClickListener { dismiss() }

    }
}