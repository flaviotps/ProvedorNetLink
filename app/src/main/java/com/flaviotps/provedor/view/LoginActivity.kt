package com.flaviotps.provedor.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.flaviotps.provedor.LOGIN_FRAGMENT_TAG
import com.flaviotps.provedor.R
import org.koin.core.component.KoinApiExtension

class LoginActivity : AppCompatActivity() {

    lateinit var access: Button

    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        access = findViewById(R.id.access)
        access.setOnClickListener {
            val loginFragment = LoginFragment()
            loginFragment.show(supportFragmentManager, LOGIN_FRAGMENT_TAG)
        }
    }

    override fun onBackPressed() {

    }
}