package com.example.chucknorries.data

import com.example.chucknorries.data.remote.ApiService
import com.example.chucknorries.domain.utils.handleNetworkException
import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import com.example.chucknorries.data.datasource.cache.mapper.mapToDatabaseEntity
import com.example.chucknorries.data.remote.mapper.toJokeEntity
import com.example.chucknorries.data.remote.mapper.toJokeListEntity
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.entities.JokesListEntity
import com.example.chucknorries.domain.utils.DataState
import com.example.chucknorries.domain.utils.ProgressBarState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class JokesRepository @Inject constructor(private val apiService: ApiService, private val cache: CacheDataSourceContract):JokesRepositoryContract {

    //Todo separate task into useCases
    override fun fetchRandomJokes(): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading(ProgressBarState.Loading))
            val response = apiService.getRandomJokes().toJokeEntity()
            emit(DataState.Data(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun searchJokes(query: String): Flow<DataState<JokesListEntity>> {
        return flow {
            emit(DataState.Loading(ProgressBarState.Loading))
            val response = apiService.searchJokes(query).toJokeListEntity()
            emit(DataState.Data(response))

        }.catch { e->
            emit(handleNetworkException(e))
        }.onCompletion {
            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }

    override fun fetchJokeCategories(): Flow<DataState<List<String>>>{
        return flow {
            emit(DataState.Loading(ProgressBarState.Loading))
            val response = apiService.getCategory()
            emit(DataState.Data(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun fetchJokeByCategory(category: String): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading())
            val response = apiService.getJokesByCategory(category).toJokeEntity()
            emit(DataState.Data(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override suspend fun favouriteJokes(entity: JokesEntity) {
        cache.insertJoke(entity.mapToDatabaseEntity())
    }

    override suspend fun isJokeExits(id: String): Boolean {
        return cache.isJokeExits(id)
    }

    override suspend fun deleteJoke(entity: JokesEntity) {
        return cache.deleteJoke(entity.mapToDatabaseEntity())
    }

    override fun fetchJokesFromCache(): Flow<DataState<List<JokesEntity>>> {
        return cache.fetchJoke().map {
            val result = it.map {data-> data.mapToDatabaseEntity() }
            DataState.Data(result)
        }.onStart {
            DataState.Loading<Nothing>(ProgressBarState.Loading)
        }.catch {e->
            handleNetworkException<Nothing>(e)
        }
    }



}