package com.example.chucknorries.data

import com.example.chucknorries.data.api.dto.Jokes
import com.example.chucknorries.data.api.dto.JokesList
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ChuckNorrisRepositoryContract {
    fun fetchRandomJokes():Flow<DataState<Jokes>>
    fun searchJokes(query:String):Flow<DataState<JokesList>>
    fun fetchJokeCategories():Flow<List<DataState<String>>>
    fun fetchJokeByCategory(category: String):Flow<List<DataState<JokesList>>>
    fun favouriteJokes(entity:JokesEntity)
    fun fetchJokesFromCache():Flow<List<JokesEntity>>
}