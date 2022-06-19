package com.example.chucknorries.data.local.dao

import androidx.room.*
import com.example.chucknorries.data.local.entities.JokesCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChuckNorrisJoke(entity: JokesCacheEntity)

    @Query("SELECT * FROM CHUCK_NORRIS_TABLE")
    fun getChuckNorrisJoke():Flow<List<JokesCacheEntity>>

    @Update
    suspend fun updateChuckNorrisJoke(entity: JokesCacheEntity)

    @Query("SELECT EXISTS(SELECT * FROM CHUCK_NORRIS_TABLE WHERE id = :id)")
    fun isExists(id : String) : Boolean

    @Delete
    fun deleteChuckNorrisJoke(entity:JokesCacheEntity)

}
