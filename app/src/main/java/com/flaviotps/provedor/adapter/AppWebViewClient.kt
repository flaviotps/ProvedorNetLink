package com.flaviotps.provedor.adapter

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import com.flaviotps.provedor.R
import com.flaviotps.provedor.utils.createWebPrintJob

class AppWebViewClient(val context: Context) : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.let { createWebPrintJob(it,context.getString(R.string.app_name), context) }
    }
}