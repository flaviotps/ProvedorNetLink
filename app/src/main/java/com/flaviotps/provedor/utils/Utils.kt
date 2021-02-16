package com.flaviotps.provedor.utils

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView

fun createWebPrintJob(webView: WebView, jobName:String, context:Context) {
    val printAdapter = webView.createPrintDocumentAdapter(jobName)
    (context.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.print(
            jobName,
            printAdapter,
            PrintAttributes.Builder().build()
    )
}


val <T> T.exhaustive: T
    get() = this