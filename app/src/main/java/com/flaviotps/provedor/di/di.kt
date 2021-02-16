package com.flaviotps.provedor.di

import androidx.lifecycle.MutableLiveData
import com.flaviotps.provedor.repository.LoginRepository
import com.flaviotps.provedor.view.LoginViewModel
import com.flaviotps.provedor.view.MainViewModel
import com.flaviotps.provedor.view.MainViewState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    single { LoginRepository() }
    single { MutableLiveData<MainViewState>() }
}