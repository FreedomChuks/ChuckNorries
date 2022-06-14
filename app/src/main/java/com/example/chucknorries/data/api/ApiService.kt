package com.example.chucknorries.data.api

import com.example.chucknorries.data.api.dto.Jokes
import com.example.chucknorries.data.api.dto.JokesList
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("jokes/random")
    suspend fun getRandomJokes(): Jokes

    @GET("jokes/search")
    suspend fun searchJokes(@Query("query") query:String): JokesList

    @GET("jokes/categories")
    suspend fun getCategory():List<String>

    @GET("jokes/random")
    suspend fun getJokesByCategory(@Query("category") category:String):Jokes

}
