package com.flaviotps.provedor.adapter


import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.*
import com.flaviotps.provedor.data.AppClient
import com.flaviotps.provedor.data.TicketInfo
import com.flaviotps.provedor.extensions.execute
import com.flaviotps.provedor.extensions.hide
import com.flaviotps.provedor.javascript.*
import com.flaviotps.provedor.view.MainViewState
import com.google.gson.Gson
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class TicketWebViewClientAdapter(private val appClient: AppClient) : WebViewClient(), KoinComponent {

    private val viewState by inject<MutableLiveData<MainViewState>>()
    private var jsonParser = Gson()

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        view?.hide()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let { it ->
            if(it.contains(LOGIN_PATH, true)){
                view?.evaluateJavascript(CHECK_LOGIN_CPF) { value ->
                    if (value.toBoolean()) {
                        view.execute(SET_CPF.replace("%cpf%", appClient.cpf))
                    } else {
                        view.execute(
                            SET_LOGIN.replace("%login%", appClient.login)
                        )
                        view.execute(
                            SET_PASSWORD.replace("%password%", appClient.password)
                        )
                    }
                    view.execute(PRESS_LOGIN)
                }
            }
            if(it.contains(OPEN_TICKETS_PATH, true)){
                view?.execute(GET_TICKET) { json ->
                    val tickets: MutableList<TicketInfo> = jsonParser.fromJson(
                            json,
                            Array<TicketInfo>::class.java
                    ).toMutableList()

                    viewState.postValue(MainViewState.WebView.Ticket.LoadedOverdue(tickets))
                }
            }
            if(it.contains(OPEN_TICKET_PATH, true) && it.contains(TICKET_QUERY, true) || it.contains(
                    PAID_TICKET_QUERY, true)){
                viewState.postValue(MainViewState.WebView.Ticket.Selected)
            }
            if(it.contains(PAID_TICKETS_PATH, true)){
                view?.execute(GET_PAID_TICKETS) { json ->
                    val tickets: MutableList<TicketInfo> = jsonParser.fromJson(
                        json,
                        Array<TicketInfo>::class.java
                    ).toMutableList()

                    viewState.postValue(MainViewState.WebView.Ticket.LoadedPaid(tickets))
                }
            }
        }
    }

    override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        viewState.postValue(MainViewState.WebView.Failed(errorCode,description,failingUrl))
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            if(it.contains(INDEX_PATH, true)){
                view?.loadUrl(PAID_TICKET_URL)
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}