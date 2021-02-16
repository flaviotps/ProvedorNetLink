package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppPlano(val name:String, val value: String, val maxUp: String , val maxDown: String, val title: String ) :
    Parcelable

