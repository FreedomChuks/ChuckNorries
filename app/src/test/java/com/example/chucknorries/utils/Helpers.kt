package com.example.chucknorries.utils


import com.example.chucknorries.data.remote.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val REQUEST_PATH: String = "/jokes/random"

private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder().build()

fun providesMoshi(): Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

internal fun makeTestApiService(mockWebServer: MockWebServer): ApiService = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(providesMoshi()))
    .build()
    .create(ApiService::class.java)


const val jokeSuccess="" +
        "{\"categories\":[],\"created_at\":\"2020-01-05 13:42:28.984661\",\"icon_url\":\"https://assets.chucknorris.host/img/avatar/chuck-norris.png\",\"id\":\"085c_ObbSFSpHwHEdurqGQ\",\"updated_at\":\"2020-01-05 13:42:28.984661\",\"url\":\"https://api.chucknorris.io/jokes/085c_ObbSFSpHwHEdurqGQ\",\"value\":\"Chuck Norris doesn't need sleep. He gets plenty every time he blinks.\"}"