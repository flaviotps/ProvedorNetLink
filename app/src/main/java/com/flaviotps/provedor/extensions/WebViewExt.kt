package com.flaviotps.provedor.extensions

import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

fun WebView.javascript(enabled : Boolean){
    this.settings.javaScriptEnabled = enabled
}

fun WebView.appCache(enabled : Boolean){
    this.settings.setAppCacheEnabled(enabled)
}

fun WebView.domStorage(enabled : Boolean){
    this.settings.domStorageEnabled = enabled
}

fun WebView.loadImages(enabled : Boolean){
    this.settings.loadsImagesAutomatically = true
}

fun WebView.controller(client: WebViewClient){
    this.webViewClient = client
}

fun WebView.execute(script:String){
    this.evaluateJavascript(script, null)

}fun WebView.execute(script:String, callback: ValueCallback<String>){
    this.evaluateJavascript(script, callback)
}