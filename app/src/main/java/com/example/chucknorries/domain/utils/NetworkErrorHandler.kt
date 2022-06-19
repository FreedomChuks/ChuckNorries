package com.example.chucknorries.domain.utils

import com.example.chucknorries.data.remote.model.ErrorResponse
import com.example.chucknorries.domain.utils.Constant.UNKNOWN_ERROR
import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException

fun <T>handleNetworkException(e:Throwable): DataState<T> {
    return when(e){
        is IOException -> DataState.Error(
            UIComponent.Dialog(
                title = "An Error Occurred",
                description = e.message?:""
            )
        )
        is HttpException -> {
            val errorResponse = convertErrorBody(e)
            DataState.Error(
                UIComponent.Dialog(
                    title = "Network Error",
                    description = errorResponse?.error?:UNKNOWN_ERROR
                ))
        }
        else->{
            DataState.Error(
                UIComponent.None(
                    description = UNKNOWN_ERROR
                )
            )
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}