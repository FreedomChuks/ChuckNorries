package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.entities.JokesCacheEntity
import kotlinx.coroutines.flow.Flow

interface CacheDataSourceContract {
    suspend fun insertJoke(entity: JokesCacheEntity)
    fun fetchJoke():Flow<List<JokesCacheEntity>>
    suspend fun isJokeExits(id:String):Boolean
    suspend fun deleteJoke(entity: JokesCacheEntity)

}