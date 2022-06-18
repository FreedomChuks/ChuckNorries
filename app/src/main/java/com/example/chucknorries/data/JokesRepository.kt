package com.example.chucknorries.data

import com.example.chucknorries.data.api.ApiService
import com.example.chucknorries.data.api.handleNetworkException
import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import com.example.chucknorries.data.mapper.mapToDatabaseEntity
import com.example.chucknorries.data.mapper.mapToEntity
import com.example.chucknorries.data.mapper.toEntity
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.entities.JokesListEntity
import com.example.chucknorries.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JokesRepository @Inject constructor(private val apiService: ApiService, private val cache: CacheDataSourceContract):JokesRepositoryContract {

    //Todo separate task into useCases
    override fun fetchRandomJokes(): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.getRandomJokes().toEntity()
            emit(DataState.Data(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun searchJokes(query: String): Flow<DataState<JokesListEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.searchJokes(query).mapToEntity()
            emit(DataState.Data(response))

        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun fetchJokeCategories(): Flow<DataState<List<String>>>{
        return flow {
            emit(DataState.Loading)
            val response = apiService.getCategory()
            emit(DataState.Data(response))
        }.catch { e->
            emit(handleNetworkException(e))
        }
    }

    override fun fetchJokeByCategory(category: String): Flow<DataState<JokesEntity>> {
        return flow {
            emit(DataState.Loading)
            val response = apiService.getJokesByCategory(category).toEntity()
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
        return flow {
            cache.fetchJoke().collect{ data->
                val result = data.map { it.mapToDatabaseEntity() }
                emit(DataState.Data(result))
            }

        }
    }


}