package com.flaviotps.provedor.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.flaviotps.provedor.R
import com.flaviotps.provedor.utils.KeyboardUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LoginFragment : BottomSheetDialogFragment() {

    lateinit var closeButton: ImageButton
    lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeButton = view.findViewById(R.id.close)
        loginButton = view.findViewById(R.id.login)
        activity?.let { KeyboardUtil(it, view) }
        loginButton.setOnClickListener { startActivity(Intent(activity,MainActivity::class.java)) }
        closeButton.setOnClickListener { dismiss() }

    }
}