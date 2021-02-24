package com.flaviotps.provedor.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TicketRequest( @SerializedName("id")
                          @Expose
                          val id:String,
                          @SerializedName("login")
                          @Expose
                          val login: String,
                          @SerializedName("password")
                          @Expose
                          val password:String)