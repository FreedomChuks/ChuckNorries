package com.example.chucknorries.domain.utils

sealed class DataState< out T>{
    class Data<T>(
        val data: T?=null
    ): DataState<T>()

    data class Loading(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<Nothing>()

    data class Error(
        val uiComponent: UIComponent
    ) : DataState<Nothing>()

}
