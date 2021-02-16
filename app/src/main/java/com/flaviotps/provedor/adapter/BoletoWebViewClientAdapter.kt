package com.flaviotps.provedor.adapter


import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.*
import com.flaviotps.provedor.data.BoletoInfo
import com.flaviotps.provedor.extensions.execute
import com.flaviotps.provedor.extensions.hide
import com.flaviotps.provedor.view.BoletoWebViewState
import com.google.gson.Gson

class BoletoWebViewClientAdapter : WebViewClient() {

    val boletoWebViewState = MutableLiveData<BoletoWebViewState>()
    private var jsonParser = Gson()

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        view?.hide()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let { it ->
            if(it.contentEquals(LOGIN_PATH)){
                view?.execute(SET_CPF)
                view?.execute(PRESS_LOGIN)
            }
            if(it.contains(BOLETOS_PATH)){
                view?.execute(GET_BOLETO) { json ->
                    val boletos: MutableList<BoletoInfo> = jsonParser.fromJson(
                            json,
                            Array<BoletoInfo>::class.java
                    ).toMutableList()

                    boletoWebViewState.postValue(BoletoWebViewState.Boleto.Loaded(boletos))
                }
            }
            if(it.contains(BOLETO_PATH, true) && it.contains(BOLETO_QUERY, true)){
                boletoWebViewState.postValue(BoletoWebViewState.Boleto.Selected)
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
        boletoWebViewState.postValue(BoletoWebViewState.LoadingStatus.Error(errorCode,description,failingUrl))
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            if(it.contains(INDEX_PATH, true)){
                view?.loadUrl(BOLETO_URL)
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}