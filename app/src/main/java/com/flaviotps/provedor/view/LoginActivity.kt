package com.flaviotps.provedor.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.flaviotps.provedor.LOGIN_FRAGMENT_TAG
import com.flaviotps.provedor.R
import org.koin.core.component.KoinApiExtension

class LoginActivity : AppCompatActivity() {

    lateinit var access: Button

    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSplash()
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        access = findViewById(R.id.access)
        access.setOnClickListener {
            val loginFragment = LoginFragment()
            loginFragment.show(supportFragmentManager, LOGIN_FRAGMENT_TAG)
        }
    }

    override fun onBackPressed() {

    }

    private fun setUpSplash() {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }
}