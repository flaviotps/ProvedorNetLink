package com.flaviotps.provedor.repository

import com.flaviotps.provedor.data.HtmlWrapper
import com.flaviotps.provedor.data.TicketRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TicketService {
    @Headers("Connection: close")
    @POST("/ticket/open")
    suspend fun getOpenTicket(@Body ticketRequest: TicketRequest): HtmlWrapper?

    @Headers("Connection: close")
    @POST("/ticket/historic/{id}")
    suspend fun getReceipt(@Body ticketRequest: TicketRequest): HtmlWrapper?
}