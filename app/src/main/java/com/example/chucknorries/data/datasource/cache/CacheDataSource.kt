package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.dao.JokesDAO
import com.example.chucknorries.data.local.entities.JokesDBEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheDataSource @Inject constructor(private val dao: JokesDAO): CacheDataSourceContract {
    override suspend fun insertJoke(entity: JokesDBEntity) {
        dao.insertChuckNorrisJoke(entity)
    }

    override fun fetchJoke(): Flow<List<JokesDBEntity>> {
       return dao.getChuckNorrisJoke()
    }

    override suspend fun isJokeExits(id:String): Boolean {
        return dao.isExists(id)
    }

    override suspend fun deleteJoke(entity: JokesDBEntity) {
       dao.deleteChuckNorrisJoke(entity)
    }


}