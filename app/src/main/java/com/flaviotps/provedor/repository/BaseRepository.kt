package com.flaviotps.provedor.repository

import com.flaviotps.provedor.BASE_API_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


open class BaseRepository {
   private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
       .connectTimeout(30, TimeUnit.SECONDS)
       .readTimeout(60, TimeUnit.SECONDS)
       .connectTimeout(60, TimeUnit.SECONDS)
       .build()
   private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    fun <T> api(service: Class<T>): T {
        return retrofit.create(service)
    }
}