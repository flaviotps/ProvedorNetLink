package com.flaviotps.provedor.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flaviotps.provedor.data.TicketRequest
import com.flaviotps.provedor.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val ticketRepository: TicketRepository): ViewModel() {

    val viewState : MutableLiveData<MainViewState> = MutableLiveData()

    fun getOpenTicket(ticketRequest: TicketRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ticketRepository.getOpenTicket(ticketRequest)?.let {
                    it.html?.let { html ->
                        viewState.postValue(MainViewState.OnOpenTicketLoaded(html))
                    }
                }
            }catch (e:Exception){
                viewState.postValue(MainViewState.OnError(e))
            }
        }
    }

    fun getReceipt(ticketRequest: TicketRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ticketRepository.getReceipt(ticketRequest)?.let {
                    it.html?.let { html ->
                        viewState.postValue(MainViewState.OnReceiptLoaded(html))
                    }
                }
            }catch (e:Exception){
                viewState.postValue(MainViewState.OnError(e))
            }
        }
    }
}