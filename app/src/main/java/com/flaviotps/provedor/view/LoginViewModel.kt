package com.flaviotps.provedor.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flaviotps.provedor.data.LoginResponse
import com.flaviotps.provedor.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class LoginViewModel(private val loginRepository: LoginRepository): ViewModel(), KoinComponent {
    val loginResponse : MutableLiveData<LoginResponse> =  MutableLiveData()
    val error : MutableLiveData<Exception> =  MutableLiveData()
    fun login(cpf:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                 loginRepository.login(cpf)?.let {
                        loginResponse.postValue(it)
                }
            }catch (e:Exception){
                error.postValue(e)
            }
        }
    }
}