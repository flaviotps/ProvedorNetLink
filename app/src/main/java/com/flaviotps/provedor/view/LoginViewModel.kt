package com.flaviotps.provedor.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flaviotps.provedor.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    val viewState : MutableLiveData<LoginViewState> = MutableLiveData()
    fun login(cpf:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                 loginRepository.login(cpf)?.let {
                     if(it.success){
                         viewState.postValue(LoginViewState.Successful(it))
                     }else {
                         viewState.postValue(LoginViewState.Invalid)
                     }
                }
            }catch (e:Exception){
                viewState.postValue(LoginViewState.Failed(e))
            }
        }
    }
}