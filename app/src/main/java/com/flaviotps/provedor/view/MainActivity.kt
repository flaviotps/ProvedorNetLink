package com.flaviotps.provedor.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.OnHistoricTicketListener
import com.flaviotps.provedor.adapter.OnTicketListener
import com.flaviotps.provedor.adapter.TicketWebViewClientAdapter
import com.flaviotps.provedor.data.LoginResponse
import com.flaviotps.provedor.data.TicketInfo
import com.flaviotps.provedor.extensions.*
import com.flaviotps.provedor.utils.createWebPrintJob
import com.flaviotps.provedor.utils.getLastCpf
import com.flaviotps.provedor.utils.setLastCpf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewState by inject<MutableLiveData<MainViewState>>()
    private val viewModel: MainViewModel by viewModel()

    private lateinit var webView: WebView
    private lateinit var welcome: TextView
    private lateinit var loading: ConstraintLayout
    private lateinit var ticketWebViewClientAdapter: TicketWebViewClientAdapter
    private lateinit var buttonHistoric : MenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        registerObservablesAndListeners()

        intent.extras?.getParcelable<LoginResponse>(EXTRA_KEY_LOGIN_RESPONSE)?.let {
            viewState.postValue(MainViewState.Login.Successful(it))
        }
    }


    private fun registerObservablesAndListeners(){

        viewState.observe(this, {
            when (it) {
                is MainViewState.Login.Successful -> {
                    it.loginResponse.client?.let { client ->

                        if(!getLastCpf(this).equals(client.cpf, true)){
                            setLastCpf(client.cpf, this)
                            CookieManager.getInstance().removeAllCookies(null);
                            CookieManager.getInstance().flush()
                        }

                            viewModel.client.postValue(client)
                            welcome.text = getString(R.string.welcome , client.name)
                            ticketWebViewClientAdapter = TicketWebViewClientAdapter(client)
                            webView.javascript(true)
                            webView.controller(ticketWebViewClientAdapter)
                            webView.appCache(true)
                            webView.domStorage(true)
                            webView.loadUrl(PAID_TICKET_URL)

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
                is MainViewState.WebView.Ticket.LoadedOverdue -> {
                    viewModel.overdueTickets.postValue(it.tickets)
                    showOverdueTickets(it.tickets)
                }
                is MainViewState.WebView.Ticket.LoadedPaid -> {
                    viewModel.paidTickets.postValue(it.tickets)
                    viewModel.client.value?.overdueBills?.let { count ->
                        if(count > 0) {
                            webView.loadUrl(TICKET_URL)
                        }else{
                            showNoOpenTickets()
                        }
                    }

                    buttonHistoric.setOnClickListener { _ ->

                        val onHistoricTicketListener = object : OnHistoricTicketListener {
                            override fun onClick(ticket: TicketInfo) {
                                ticket.link?.let { link ->
                                    webView.loadUrl(link)
                                }
                            }
                        }

                        val historicFragment = HistoricTicketFragment(it.tickets, onHistoricTicketListener)
                        historicFragment.show(supportFragmentManager, HISTORIC_TICKET_FRAGMENT_TAG)
                    }
                }
                is MainViewState.WebView.Failed -> {
                    it.description?.let { description ->
                        Log.e(MainActivity::class.java.simpleName, description)
                    }
                }
            }
        })
    }

    private fun showNoOpenTickets() {
        supportFragmentManager.beginTransaction()
                .add(R.id.ticketsContainerView, CongratulationsFragment())
                .commit()
    }

    private fun showOverdueTickets(it: MutableList<TicketInfo>) {
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
        buttonHistoric = findViewById(R.id.historic)
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