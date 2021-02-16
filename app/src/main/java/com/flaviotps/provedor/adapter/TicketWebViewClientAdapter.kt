package com.flaviotps.provedor.adapter


import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.*
import com.flaviotps.provedor.data.TicketInfo
import com.flaviotps.provedor.extensions.execute
import com.flaviotps.provedor.extensions.hide
import com.flaviotps.provedor.javascript.GET_TICKET
import com.flaviotps.provedor.javascript.PRESS_LOGIN
import com.flaviotps.provedor.javascript.SET_CPF
import com.flaviotps.provedor.view.MainViewState
import com.google.gson.Gson
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class TicketWebViewClientAdapter(private val cpf: String) : WebViewClient(), KoinComponent {

    private val viewState by inject<MutableLiveData<MainViewState>>()
    private var jsonParser = Gson()

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        view?.hide()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let { it ->
            if(it.contentEquals(LOGIN_PATH)){
                view?.execute(SET_CPF.replace("%cpf%",cpf))
                view?.execute(PRESS_LOGIN)
            }
            if(it.contains(TICKETS_PATH)){
                view?.execute(GET_TICKET) { json ->
                    val tickets: MutableList<TicketInfo> = jsonParser.fromJson(
                            json,
                            Array<TicketInfo>::class.java
                    ).toMutableList()

                    viewState.postValue(MainViewState.WebView.Ticket.Loaded(tickets))
                }
            }
            if(it.contains(TICKET_PATH, true) && it.contains(TICKET_QUERY, true)){
                viewState.postValue(MainViewState.WebView.Ticket.Selected)
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
                view?.loadUrl(TICKET_URL)
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}