package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.entities.ChuckNorrisEntity
import kotlinx.coroutines.flow.Flow

interface CacheDataSourceContract {
    suspend fun insertJoke(entity: List<ChuckNorrisEntity>)
    fun fetchJoke():Flow<List<ChuckNorrisEntity>>
    suspend fun updateJoke(entity: ChuckNorrisEntity)

}