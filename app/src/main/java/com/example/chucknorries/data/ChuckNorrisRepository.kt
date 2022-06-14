package com.example.chucknorries.data

import com.example.chucknorries.data.api.ApiService
import com.example.chucknorries.data.api.dto.Jokes
import com.example.chucknorries.data.api.dto.JokesList
import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChuckNorrisRepository @Inject constructor(apiService: ApiService,cache: CacheDataSourceContract):ChuckNorrisRepositoryContract {
    //Todo seperate task into usecases

    override fun fetchRandomJokes(): Flow<DataState<Jokes>> {
        TODO("Not yet implemented")
    }

    override fun searchJokes(query: String): Flow<DataState<JokesList>> {
        TODO("Not yet implemented")
    }

    override fun fetchJokeCategories(): Flow<List<DataState<String>>> {
        TODO("Not yet implemented")
    }

    override fun fetchJokeByCategory(category: String): Flow<List<DataState<JokesList>>> {
        TODO("Not yet implemented")
    }

    override fun favouriteJokes(entity: JokesEntity) {
        TODO("Not yet implemented")
    }

    override fun fetchJokesFromCache(): Flow<List<JokesEntity>> {
        TODO("Not yet implemented")
    }


}