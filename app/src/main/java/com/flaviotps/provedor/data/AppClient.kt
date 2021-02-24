package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppClient(val name: String? = null,
                val cpf: String? = null,
                val blocked: String?= null ,
                val email: String?= null ,
                val agreement: String?= null,
                val login: String?= null,
                val password: String?= null,
                val enabled: String?= null,
                val overdueBills: Int?= null) : Parcelable
