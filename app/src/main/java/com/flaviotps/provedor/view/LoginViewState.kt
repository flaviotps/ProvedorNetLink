package com.flaviotps.provedor.view

import com.flaviotps.provedor.data.LoginResponse
import java.lang.Exception

open class LoginViewState {
        class Successful(val loginResponse: LoginResponse) : LoginViewState()
        object Invalid : LoginViewState()
        class Failed(val e:Exception) : LoginViewState()
    }