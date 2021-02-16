package com.flaviotps.provedor.repository

import com.flaviotps.provedor.LOGIN_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class BaseRepository {
    var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(LOGIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> api(service: Class<T>): T {
        return retrofit.create(service)
    }
}