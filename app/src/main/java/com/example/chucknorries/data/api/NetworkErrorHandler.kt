package com.example.chucknorries.data.api

import com.example.chucknorries.data.api.dto.ErrorResponse
import com.example.chucknorries.domain.utils.DataState
import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException

fun <T>handleNetworkException(e:Throwable): DataState<T> {
return when(e){
    is IOException -> DataState.NetworkError(e.message.toString())
    is HttpException -> {
        val errorResponse = convertErrorBody(e)
        DataState.Error(errorResponse)
    }
    else->{
        DataState.NetworkError(e.message?:"")
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