package com.flaviotps.provedor.repository

import com.flaviotps.provedor.data.LoginResponse

class LoginRepository : BaseRepository() {
    suspend fun login(cpf:String): LoginResponse? {
        return api(LoginService::class.java).login(cpf)
    }
}