package com.example.chucknorries.data.repository.fake


import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import com.example.chucknorries.data.local.entities.JokesCacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JokeFakeDAO(
    private val fakeDatabase: JokeFakeDatabase
): CacheDataSourceContract {
    override suspend fun insertJoke(entity: JokesCacheEntity) {
        fakeDatabase.database.add(entity)
    }

    override fun fetchJoke(): Flow<List<JokesCacheEntity>> {
        return flow {
            emit(fakeDatabase.database)
        }
    }

    override suspend fun isJokeExits(id: String): Boolean {
        return true
    }

    override suspend fun deleteJoke(entity: JokesCacheEntity) {
        fakeDatabase.database.clear()
    }

}