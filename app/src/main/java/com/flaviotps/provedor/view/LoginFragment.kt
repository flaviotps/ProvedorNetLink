package com.flaviotps.provedor.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.flaviotps.provedor.EXTRA_KEY_LOGIN_RESPONSE
import com.flaviotps.provedor.R
import com.flaviotps.provedor.extensions.hide
import com.flaviotps.provedor.extensions.show
import com.flaviotps.provedor.utils.KeyboardUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BottomSheetDialogFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    lateinit var closeButton: ImageButton
    lateinit var loadingLayout: ConstraintLayout
    lateinit var loginButton: Button
    lateinit var cpfInputText: TextInputEditText
    lateinit var cpfInputLayout: TextInputLayout

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
        cpfInputLayout = view.findViewById(R.id.cpfInputLayout)
        loadingLayout = view.findViewById(R.id.loadingLayout)
        activity?.let { KeyboardUtil(it, view) }
        loginButton.setOnClickListener {
            loadingLayout.show()
            isCancelable = false
            loginViewModel.login(cpfInputText.text.toString())
        }
        closeButton.setOnClickListener { dismiss() }

        loginViewModel.viewState.observe(this, {

            when(it){
                is LoginViewState.Successful -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra(EXTRA_KEY_LOGIN_RESPONSE, it.loginResponse)
                    startActivity(intent)
                    activity?.finish()
                }
                is LoginViewState.Failed -> {
                    loadingLayout.hide()
                    isCancelable = true
                    cpfInputLayout.error = it.e.message
                }
                is LoginViewState.Invalid -> {
                    loadingLayout.hide()
                    isCancelable = true
                    cpfInputLayout.error = "CPF Inválido"
                }
            }
        })
    }
}