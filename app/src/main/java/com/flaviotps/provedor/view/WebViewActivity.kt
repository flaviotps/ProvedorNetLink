package com.flaviotps.provedor.view

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.flaviotps.provedor.*
import com.flaviotps.provedor.adapter.AppWebViewClient
import com.flaviotps.provedor.extensions.appCache
import com.flaviotps.provedor.extensions.controller
import com.flaviotps.provedor.extensions.javascript

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var appWebViewClient: AppWebViewClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webview)
        appWebViewClient = AppWebViewClient(this)
        webView.javascript(true)
        webView.controller(appWebViewClient)
        webView.appCache(true)
        webView.settings.setSupportZoom(true);
        webView.settings.builtInZoomControls = true;
        webView.settings.useWideViewPort = true;
        webView.settings.loadWithOverviewMode = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.allowFileAccess = true
        intent.extras?.getString(EXTRA_KEY_TICKET_GWT)?.let { gwt ->
            webView.loadUrl("${BASE_SITE_URL}/boleto/18boleto.php?titulo=${gwt}")
        }
        intent.extras?.getString(EXTRA_KEY_HTML_RESPONSE)?.let { html ->
            webView.loadData(html, "text/html; charset=utf-8", "base64")
        }
    }
}