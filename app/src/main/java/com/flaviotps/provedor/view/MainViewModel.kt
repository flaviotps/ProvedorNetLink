package com.flaviotps.provedor.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val mainViewState = MutableLiveData<MainViewState>()
}