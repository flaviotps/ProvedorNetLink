package com.flaviotps.provedor.adapter

import android.content.Context
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.flaviotps.provedor.R
import com.flaviotps.provedor.utils.createWebPrintJob
import java.lang.Exception

class AppWebViewClient(val context: Context) : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        try {
            view?.let { createWebPrintJob(it, context.getString(R.string.app_name), context) }
        }catch (e:Exception){
            Log.e("AppWebViewClient", e.message)
        }
    }
}