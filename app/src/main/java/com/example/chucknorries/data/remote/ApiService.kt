package com.example.chucknorries.data.remote

import com.example.chucknorries.data.remote.model.JokesResponse
import com.example.chucknorries.data.remote.model.JokesListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("jokes/random")
    suspend fun getRandomJokes(): JokesResponse

    @GET("jokes/search")
    suspend fun searchJokes(@Query("query") query:String): JokesListResponse

    @GET("jokes/categories")
    suspend fun getCategory():List<String>

    @GET("jokes/random")
    suspend fun getJokesByCategory(@Query("category") category:String): JokesResponse

}
