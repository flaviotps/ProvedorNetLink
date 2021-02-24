package com.flaviotps.provedor.repository

import com.flaviotps.provedor.data.HtmlWrapper
import com.flaviotps.provedor.data.TicketRequest

class TicketRepository : BaseRepository() {
    suspend fun getOpenTicket(ticketRequest: TicketRequest): HtmlWrapper? {
        return api(TicketService::class.java).getOpenTicket(ticketRequest)
    }
    suspend fun getReceipt(ticketRequest: TicketRequest): HtmlWrapper? {
        return api(TicketService::class.java).getReceipt(ticketRequest)
    }
}