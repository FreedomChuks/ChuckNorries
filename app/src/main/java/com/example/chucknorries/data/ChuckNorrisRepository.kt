package com.example.chucknorries.data

import com.example.chucknorries.data.api.ApiService
import com.example.chucknorries.data.api.handleNetworkException
import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import com.example.chucknorries.data.mapper.mapToDatabaseEntity
import com.example.chucknorries.data.mapper.mapToDomain
import com.example.chucknorries.data.mapper.mapToEntity
import com.example.chucknorries.data.mapper.toEntity
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.domain.JokesListEntity
import com.example.chucknorries.domain.utils.DataState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChuckNorrisRepository @Inject constructor(private val apiService: ApiService, private val cache: CacheDataSourceContract):ChuckNorrisRepositoryContract {
    //Todo separate task into useCases
    override fun fetchRandomJokes(): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.getRandomJokes().toEntity()
            emit(DataState.Success(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun searchJokes(query: String): Flow<DataState<JokesListEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.searchJokes(query).mapToEntity()
            emit(DataState.Success(response))

        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun fetchJokeCategories(): Flow<DataState<List<String>>>{
        return flow {
            emit(DataState.Loading)
            val response = apiService.getCategory()
            emit(DataState.Success(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }
    override fun fetchJokeByCategory(category: String): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.getJokesByCategory(category).toEntity()
            emit(DataState.Success(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override suspend fun favouriteJokes(entity: JokesEntity) {
        cache.insertJoke(entity.mapToDomain())
    }

    override fun fetchJokesFromCache(): Flow<DataState<List<JokesEntity>>> {
        return flow {
            cache.fetchJoke().collect{
                val result = it.map { it.mapToDatabaseEntity() }
                emit(DataState.Success(result))
            }

        }
    }


}