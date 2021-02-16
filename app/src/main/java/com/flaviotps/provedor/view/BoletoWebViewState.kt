package com.flaviotps.provedor.view

import com.flaviotps.provedor.data.BoletoInfo

sealed class BoletoWebViewState {

    sealed class LoadingStatus : BoletoWebViewState() {
         class Error(val errorCode: Int,
                           val description: String?,
                           val failingUrl: String?) : LoadingStatus()
    }
    sealed class Boleto : BoletoWebViewState(){
        class Loaded(val boletos: MutableList<BoletoInfo>) : Boleto()
        object Selected : Boleto()
    }
}