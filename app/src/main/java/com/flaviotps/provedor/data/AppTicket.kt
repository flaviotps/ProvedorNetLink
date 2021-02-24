package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppTicket(val id:String? = null, val value: String?= null, val dueDate: String?= null) : Parcelable

