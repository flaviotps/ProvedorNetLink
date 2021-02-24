package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppTickets(var open:MutableList<AppTicket>? = null, var historic:MutableList<AppTicket>? = null) : Parcelable

