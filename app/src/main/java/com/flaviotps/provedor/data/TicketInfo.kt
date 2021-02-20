package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketInfo(val link:String?, val value:String?, val dueDate:String?) : Parcelable
