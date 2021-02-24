package com.flaviotps.provedor.di

import com.flaviotps.provedor.repository.LoginRepository
import com.flaviotps.provedor.repository.TicketRepository
import com.flaviotps.provedor.view.LoginViewModel
import com.flaviotps.provedor.view.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    single { LoginRepository() }
    single { TicketRepository() }
}