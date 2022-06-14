package com.example.chucknorries.domain.utils

import com.example.chucknorries.data.api.dto.ErrorResponse

sealed class DataState< out T>(
    val data: T?=null,
    val errorResponse: ErrorResponse?=null,
    val systemError:String?=null,
    val isLoading: Boolean = false
){
    class Success<T>( datas: T): DataState<T>(datas)
    object Loading: DataState<Nothing>(isLoading = true)
    class Error(exception: ErrorResponse?=null): DataState<Nothing>(errorResponse = exception)
    class NetworkError(systemError: String?=null): DataState<Nothing>(systemError = systemError)
}
