package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.dao.JokesDAO
import com.example.chucknorries.data.local.entities.JokesCacheEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheDataSource @Inject constructor(private val dao: JokesDAO): CacheDataSourceContract {
    override suspend fun insertJoke(entity: JokesCacheEntity) {
        dao.insertChuckNorrisJoke(entity)
    }

    override fun fetchJoke(): Flow<List<JokesCacheEntity>> {
       return dao.getChuckNorrisJoke()
    }

    override suspend fun isJokeExits(id:String): Boolean {
        return dao.isExists(id)
    }

    override suspend fun deleteJoke(entity: JokesCacheEntity) {
       dao.deleteChuckNorrisJoke(entity)
    }


}