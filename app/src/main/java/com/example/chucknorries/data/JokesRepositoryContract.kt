package com.example.chucknorries.data

import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.entities.JokesListEntity
import com.example.chucknorries.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface JokesRepositoryContract {
    fun fetchRandomJokes():Flow<DataState<JokesEntity>>
    fun searchJokes(query:String):Flow<DataState<JokesListEntity>>
    fun fetchJokeCategories():Flow<DataState<List<String>>>
    fun fetchJokeByCategory(category: String):Flow<DataState<JokesEntity>>
    suspend fun favouriteJokes(entity: JokesEntity)
    suspend fun isJokeExits(id:String):Boolean
    suspend fun deleteJoke(entity: JokesEntity)
    fun fetchJokesFromCache():Flow<DataState<List<JokesEntity>>>
}