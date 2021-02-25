package com.flaviotps.provedor.view

import com.flaviotps.provedor.data.LoginResponse
import java.lang.Exception

open class MainViewState {
    class OnLoginSuccessful(val loginResponse: LoginResponse) : MainViewState()
    class OnOpenTicketLoaded(val html:String?) : MainViewState()
    class OnReceiptLoaded(val html:String?) : MainViewState()
    class OnError(val exception: Exception) : MainViewState()
    object OnLoading : MainViewState()
}