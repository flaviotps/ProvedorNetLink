package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginResponse(
        var success: Boolean = false,
        var client: AppClient? = null,
        var plan : AppPlano? = null) : Parcelable