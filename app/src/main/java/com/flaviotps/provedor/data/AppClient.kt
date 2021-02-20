package com.flaviotps.provedor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AppClient(val name: String,
                      val cpf: String,
                      val blocked: String,
                      val email: String,
                      val agreement: String,
                      val login: String,
                      val password: String,
                      val enabled: String,
                      val overdueBills: Int) : Parcelable
