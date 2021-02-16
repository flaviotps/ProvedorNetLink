package com.flaviotps.provedor.repository

import com.flaviotps.provedor.data.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginService {
    @GET("/clientes/cpf/{cpf}")
    suspend fun login(@Path("cpf") cpf: String?): LoginResponse?
}