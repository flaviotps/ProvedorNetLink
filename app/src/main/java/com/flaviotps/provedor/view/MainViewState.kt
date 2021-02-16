package com.flaviotps.provedor.view

sealed class MainViewState {
    sealed class LoadingStatus : MainViewState() {
        object Loading : LoadingStatus()
        object Loaded : LoadingStatus()
        object Error : LoadingStatus()
    }
}