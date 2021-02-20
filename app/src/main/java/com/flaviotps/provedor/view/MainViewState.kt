package com.flaviotps.provedor.view

import com.flaviotps.provedor.data.LoginResponse
import com.flaviotps.provedor.data.TicketInfo
import java.lang.Exception

open class MainViewState {
    sealed class Login : MainViewState() {
        class Successful(val loginResponse: LoginResponse) : Login()
        object Invalid : Login()
        class Failed(val e:Exception) : Login()
    }
    sealed class WebView : MainViewState() {
        class Failed(val errorCode: Int,
                     val description: String?,
                     val failingUrl: String?) : WebView()
        sealed class Ticket : MainViewState(){
            class LoadedOverdue(val tickets: MutableList<TicketInfo>) : Ticket()
            class LoadedPaid(val tickets: MutableList<TicketInfo>) : Ticket()
            object Selected : Ticket()
        }
    }
}