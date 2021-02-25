package com.flaviotps.provedor.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.OnHistoricTicketListener
import com.flaviotps.provedor.adapter.OnTicketListener
import com.flaviotps.provedor.data.AppClient
import com.flaviotps.provedor.data.AppTicket
import com.flaviotps.provedor.data.LoginResponse
import com.flaviotps.provedor.data.TicketRequest
import com.flaviotps.provedor.extensions.hide
import com.flaviotps.provedor.extensions.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModel()

    private lateinit var welcome: TextView
    private lateinit var buttonHistoric: MenuButton
    private lateinit var buttonMyPlan: MenuButton
    private lateinit var buttonAgreement: MenuButton
    private lateinit var buttonUnlock: MenuButton
    lateinit var loadingLayout: ConstraintLayout

    private var historicFragment : HistoricTicketFragment? = null
    private var planFragment : PlanFragment? = null
    private var ticketFragment :TicketFragment? = null
    private var openTicketFragment : OpenTicketFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        registerObservablesAndListeners()

        intent.extras?.getParcelable<LoginResponse>(EXTRA_KEY_LOGIN_RESPONSE)?.let {
            viewModel.viewState.postValue(MainViewState.OnLoginSuccessful(it))
        }
    }

    private fun registerObservablesAndListeners() {

        viewModel.viewState.observe(this, {
            when (it) {

                is MainViewState.OnLoading -> {
                    loadingLayout.show()
                }

                is MainViewState.OnOpenTicketLoaded -> {
                    loadingLayout.hide()
                    it.html?.let { html ->
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra(EXTRA_KEY_HTML_RESPONSE, html)
                        startActivity(intent)
                    }
                }

                is MainViewState.OnReceiptLoaded -> {
                    loadingLayout.hide()
                    it.html?.let { html ->
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra(EXTRA_KEY_HTML_RESPONSE, html)
                        startActivity(intent)
                    }
                }

                is MainViewState.OnLoginSuccessful -> {
                    addListeners(it)
                    addOpenTickets(it)
                    addTicketHistoric(it)
                }

                is MainViewState.OnError -> {
                    it.exception.message?.let { msg ->
                        Log.e("MainActivity", msg)
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                    }
                    loadingLayout.hide()
                }
            }
        })
    }

    private fun addTicketHistoric(it: MainViewState.OnLoginSuccessful) {
        it.loginResponse.tickets?.historic?.let { ticketsHistoric ->
            if (ticketsHistoric.isNotEmpty()) {
                buttonHistoric.setOnClickListener { _ ->
                    val onHistoricTicketListener = object : OnHistoricTicketListener {
                        override fun onClick(ticket: AppTicket) {
                            historicFragment?.dismiss()
                            it.loginResponse.client?.let { client ->
                                if (!client.login.isNullOrEmpty() && !client.password.isNullOrEmpty() && !ticket.id.isNullOrEmpty()) {
                                    viewModel.getReceipt(TicketRequest(id = ticket.id, login = client.login, password = client.password))
                                }
                            }
                        }
                    }

                    historicFragment = HistoricTicketFragment(
                            ticketsHistoric,
                            onHistoricTicketListener
                    )
                    historicFragment?.show(supportFragmentManager, HISTORIC_TICKET_FRAGMENT_TAG)
                }
            }
        }
    }

    private fun addOpenTickets(it: MainViewState.OnLoginSuccessful) {
        it.loginResponse.client?.let { client ->
            welcome.text = getString(R.string.welcome, client.name)
        }

        it.loginResponse.tickets?.open?.let { openTickets ->
            if (openTickets.isEmpty()) {
                showNoOpenTickets()
            } else {
                showOpenTickets(it.loginResponse.client, openTickets)
            }
        }
    }

    private fun addListeners(it: MainViewState.OnLoginSuccessful) {

        buttonMyPlan.setOnClickListener { _ ->
            if (it.loginResponse.plan != null) {
                it.loginResponse.plan?.let { plan ->
                    planFragment = PlanFragment(plan)
                    planFragment?.show(supportFragmentManager, PLAN_FRAGMENT_TAG)
                }
            } else {
                Toast.makeText(
                        this,
                        "Dados do plano inválidos",
                        Toast.LENGTH_LONG
                ).show()
            }
            it.loginResponse.plan?.let { plan ->

            }
        }

        buttonAgreement.setOnClickListener { _ ->
            it.loginResponse.client?.agreement?.let { agreement ->
                Toast.makeText(
                        this,
                        "Em desenvolvimento (contrato: ${agreement})",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
        buttonUnlock.setOnClickListener { _ ->
            it.loginResponse.client?.blocked?.let { blocked ->
                Toast.makeText(this, "Em desenvolvimento (blocked: ${blocked})", Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun showNoOpenTickets() {
        supportFragmentManager.beginTransaction()
                .add(R.id.ticketsContainerView, CongratulationsFragment())
                .commit()
    }

    private fun showOpenTickets(client: AppClient?, it: MutableList<AppTicket>) {
        val onTicketListener = object : OnTicketListener {
            override fun onClick(ticket: AppTicket) {
                client?.let {
                    ticketFragment = TicketFragment(client,ticket)
                    ticketFragment?.show(supportFragmentManager, OPEN_TICKET_FRAGMENT_TAG)
                }
            }
        }

        openTicketFragment = OpenTicketFragment(it, onTicketListener).apply {
            supportFragmentManager.beginTransaction()
                    .add(R.id.ticketsContainerView, this)
                    .commit()
        }
    }

    private fun initViews() {
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_custom)
            setDisplayHomeAsUpEnabled(false)
        }
        welcome = findViewById(R.id.welcome)
        buttonHistoric = findViewById(R.id.historic)
        buttonUnlock = findViewById(R.id.buttonUnlock)
        buttonAgreement = findViewById(R.id.buttonAgreement)
        buttonMyPlan = findViewById(R.id.buttonMyPlan)
        loadingLayout = findViewById(R.id.loadingLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
    }
}