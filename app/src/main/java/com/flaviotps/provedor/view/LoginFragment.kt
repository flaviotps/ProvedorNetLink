package com.flaviotps.provedor.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.flaviotps.provedor.EXTRA_KEY_LOGIN_RESPONSE
import com.flaviotps.provedor.R
import com.flaviotps.provedor.utils.KeyboardUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class LoginFragment : BottomSheetDialogFragment() {

    private val mainViewModel: LoginViewModel by viewModel()

    lateinit var closeButton: ImageButton
    lateinit var loginButton: Button
    lateinit var cpfInputText: TextInputEditText

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
        cpfInputText = view.findViewById(R.id.cpfInputText)
        activity?.let { KeyboardUtil(it, view) }
        loginButton.setOnClickListener { mainViewModel.login(cpfInputText.text.toString()) }
        closeButton.setOnClickListener { dismiss() }

        mainViewModel.loginResponse.observe(this, {
            if(it.success){
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra(EXTRA_KEY_LOGIN_RESPONSE, it)
                startActivity(intent)
                activity?.finish()
            }else{
                //TODO SHOW ERROR
            }

        })
        mainViewModel.error.observe(this, {
            it.message?.let { msg ->
                Log.e(LoginFragment::class.java.simpleName, msg)
            }
        })

    }
}