package com.flaviotps.provedor.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flaviotps.provedor.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

@KoinApiExtension
class MainViewModel(private val loginRepository: LoginRepository): ViewModel(), KoinComponent {
    private val viewState by inject<MutableLiveData<MainViewState>>()
    fun login(cpf:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                 loginRepository.login(cpf)?.let {
                    if (it.success) {
                        viewState.postValue(MainViewState.Login.Successful(it))
                    } else {
                        viewState.postValue(MainViewState.Login.Invalid)
                    }
                }
            }catch (e:Exception){
                viewState.postValue(MainViewState.Login.Failed(e))
            }
        }
    }
}