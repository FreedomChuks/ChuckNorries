package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.entities.JokesDBEntity
import kotlinx.coroutines.flow.Flow

interface CacheDataSourceContract {
    suspend fun insertJoke(entity: JokesDBEntity)
    fun fetchJoke():Flow<List<JokesDBEntity>>
    suspend fun isJokeExits(id:String):Boolean
    suspend fun deleteJoke(entity: JokesDBEntity)

}