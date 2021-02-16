package com.flaviotps.provedor.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.OnTicketListener
import com.flaviotps.provedor.adapter.TicketWebViewClientAdapter
import com.flaviotps.provedor.data.LoginResponse
import com.flaviotps.provedor.data.TicketInfo
import com.flaviotps.provedor.extensions.*
import com.flaviotps.provedor.utils.createWebPrintJob
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewState by inject<MutableLiveData<MainViewState>>()

    private lateinit var webView: WebView
    private lateinit var welcome: TextView
    private lateinit var loading: ConstraintLayout
    private lateinit var ticketWebViewClientAdapter: TicketWebViewClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        registerObservablesAndListeners()

        val loginResponse = intent.extras?.getParcelable<LoginResponse>(EXTRA_KEY_LOGIN_RESPONSE)!!
        viewState.postValue(MainViewState.Login.Successful(loginResponse))
    }


    private fun registerObservablesAndListeners(){

        viewState.observe(this, {
            when (it) {
                is MainViewState.Login.Successful -> {
                    it.loginResponse.client?.let { client ->
                            welcome.text = getString(R.string.welcome , client.name)
                            ticketWebViewClientAdapter = TicketWebViewClientAdapter(client.cpf)
                            webView.javascript(true)
                            webView.controller(ticketWebViewClientAdapter)
                            webView.appCache(true)
                            webView.domStorage(true)
                            webView.loadUrl(TICKET_URL)
                    }
                }
                is MainViewState.Login.Failed -> {
                    Log.i(MainActivity::class.java.simpleName,"Failed")
                }
                is MainViewState.Login.Invalid -> {
                    Log.i(MainActivity::class.java.simpleName,"Invalid")
                }
                is MainViewState.WebView.Ticket.Selected -> {
                    showTicket()
                }
                is MainViewState.WebView.Ticket.Loaded -> {
                    if(!it.tickets.isNullOrEmpty()) {
                        showTickets(it.tickets)
                    }else{
                        showNoOpenTickets()
                    }
                }
                is MainViewState.WebView.Failed -> {
                    //TODO
                }
            }
        })
    }

    private fun showNoOpenTickets() {
        supportFragmentManager.beginTransaction()
                .add(R.id.ticketsContainerView, CongratulationsFragment())
                .commit()
    }

    private fun showTickets(it: MutableList<TicketInfo>) {
        val onTicketListener = object : OnTicketListener {
            override fun onClick(ticket: TicketInfo) {
                webView.loadUrl(ticket.link)
            }
        }

        val ticketFragment = TicketFragment(it, onTicketListener)
        supportFragmentManager.beginTransaction()
                .add(R.id.ticketsContainerView, ticketFragment)
                .commit()
    }

    private fun showTicket() {
        loading.hide()
        createWebPrintJob(webView, getString(R.string.app_name), this@MainActivity)
    }

    private fun initViews(){
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_custom)
            setDisplayHomeAsUpEnabled(false)
        }
        webView = findViewById(R.id.webview)
        loading = findViewById(R.id.loading)
        welcome = findViewById(R.id.welcome)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.exit -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
    }
}