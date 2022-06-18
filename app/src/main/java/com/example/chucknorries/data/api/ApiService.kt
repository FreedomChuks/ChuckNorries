package com.example.chucknorries.data.api

import com.example.chucknorries.data.api.dto.JokesDTO
import com.example.chucknorries.data.api.dto.JokesListDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("jokes/random")
    suspend fun getRandomJokes(): JokesDTO

    @GET("jokes/search")
    suspend fun searchJokes(@Query("query") query:String): JokesListDTO

    @GET("jokes/categories")
    suspend fun getCategory():List<String>

    @GET("jokes/random")
    suspend fun getJokesByCategory(@Query("category") category:String):JokesDTO

}
