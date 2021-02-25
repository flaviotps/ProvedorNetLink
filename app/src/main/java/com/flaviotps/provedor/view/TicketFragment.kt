package com.flaviotps.provedor.view

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.AppClient
import com.flaviotps.provedor.data.AppTicket
import com.flaviotps.provedor.data.TicketRequest
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TicketFragment(private val client: AppClient, private val ticket: AppTicket)  : BottomSheetDialogFragment() {

    private lateinit var closeButton: ImageButton
    private lateinit var code: Button
    private lateinit var pdf: Button
    private val viewModel: MainViewModel by sharedViewModel()

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
            if (!client.login.isNullOrEmpty() && !client.password.isNullOrEmpty() && !ticket.id.isNullOrEmpty()) {
                viewModel.getOpenTicket(TicketRequest(id = ticket.id, login = client.login, password = client.password))
                dismiss()
            }
        }
        closeButton.setOnClickListener { dismiss() }

    }
}