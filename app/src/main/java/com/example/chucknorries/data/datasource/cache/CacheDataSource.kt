package com.example.chucknorries.data.datasource.cache


import com.example.chucknorries.data.local.dao.ChuckNorrisDAO
import com.example.chucknorries.data.local.entities.ChuckNorrisEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheDataSource @Inject constructor(private val dao: ChuckNorrisDAO): CacheDataSourceContract {
    override suspend fun insertJoke(entity: List<ChuckNorrisEntity>) {
        dao.insertChuckNorrisJoke(entity)
    }

    override fun fetchJoke(): Flow<List<ChuckNorrisEntity>> {
       return dao.getChuckNorrisJoke()
    }

    override suspend fun updateJoke(entity: ChuckNorrisEntity) {
       dao.updateChuckNorrisJoke(entity)
    }


}