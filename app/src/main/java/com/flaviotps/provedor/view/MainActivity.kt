package com.flaviotps.provedor.view

import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.BoletoWebViewClientAdapter
import com.flaviotps.provedor.adapter.OnBoletoListener
import com.flaviotps.provedor.data.BoletoInfo
import com.flaviotps.provedor.extensions.*
import com.flaviotps.provedor.utils.createWebPrintJob
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var webView: WebView
    private lateinit var loading: ConstraintLayout
    private lateinit var showBoletos: Button
    private var boletosFragment: BoletosFragment? = null

    private val boletoController by lazy {
        BoletoWebViewClientAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boleto)
        initViews()
        registerObservablesAndListeners()

        webView.javascript(true)
        webView.controller(boletoController)
        webView.appCache(true)
        webView.domStorage(true)
        webView.loadUrl(BOLETO_URL)
    }


    private fun registerObservablesAndListeners(){

        boletoController.boletoWebViewState.observe(this, {
            when (it) {
                is BoletoWebViewState.Boleto.Selected -> {
                    loading.hide()
                    createWebPrintJob(webView, getString(R.string.app_name), this@MainActivity)
                }
                is BoletoWebViewState.Boleto.Loaded -> {
                    val onBoletoListener = object : OnBoletoListener {
                        override fun onClick(boleto: BoletoInfo) {
                            webView.loadUrl(boleto.link)
                        }
                    }
                    boletosFragment = BoletosFragment(it.boletos, onBoletoListener)
                }
                is BoletoWebViewState.LoadingStatus.Error -> {
                    //TODO
                }
            }
        })

        showBoletos.setOnClickListener {
            boletosFragment?.show(supportFragmentManager, BOLETO_FRAGMENT_TAG)
        }

    }

      private fun initViews(){
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_custom)
            setDisplayHomeAsUpEnabled(false)
        }
        webView = findViewById(R.id.webview)
        loading = findViewById(R.id.loading)
        showBoletos = findViewById(R.id.showBoletos)
    }
}