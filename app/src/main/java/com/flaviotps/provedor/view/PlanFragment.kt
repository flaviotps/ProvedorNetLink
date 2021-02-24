package com.flaviotps.provedor.view

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.flaviotps.provedor.R
import com.flaviotps.provedor.data.AppPlan
import com.flaviotps.provedor.extensions.fromHtml
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlanFragment (private val plan: AppPlan) : BottomSheetDialogFragment() {

    private lateinit var name: TextView
    private lateinit var value: TextView
    private lateinit var download: TextView
    private lateinit var upload: TextView
    private lateinit var closeButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeButton = view.findViewById(R.id.close)
        name = view.findViewById(R.id.name)
        value = view.findViewById(R.id.value)
        download = view.findViewById(R.id.download)
        upload = view.findViewById(R.id.upload)

        closeButton.setOnClickListener { dismiss() }
        plan.maxDown?.let { download.fromHtml("Download <b>${it}</b> Mbits/s ") }
        plan.maxUp?.let { upload.fromHtml("Upload <b>${it}</b> Mbits/s") }
        plan.value?.let { value.fromHtml("Valor <b>R$${it}</b>") }
        plan.title?.let { name.fromHtml("Plano <b>${it}</b>") }
    }


}