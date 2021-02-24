package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppPlan(val name:String? = null, val value: String? = null, val maxUp: String? = null, val maxDown: String?= null, val title: String?= null) : Parcelable

