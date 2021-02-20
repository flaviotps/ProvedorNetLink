package com.flaviotps.provedor.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flaviotps.provedor.data.AppClient
import com.flaviotps.provedor.data.TicketInfo
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class MainViewModel: ViewModel(), KoinComponent {
    val client = MutableLiveData<AppClient>()
    val paidTickets = MutableLiveData<MutableList<TicketInfo>>()
    val overdueTickets = MutableLiveData<MutableList<TicketInfo>>()
}