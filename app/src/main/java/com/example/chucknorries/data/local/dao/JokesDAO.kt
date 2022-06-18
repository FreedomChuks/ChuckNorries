package com.example.chucknorries.data.local.dao

import androidx.room.*
import com.example.chucknorries.data.local.entities.JokesDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChuckNorrisJoke(entity: JokesDBEntity)

    @Query("SELECT * FROM CHUCK_NORRIS_TABLE")
    fun getChuckNorrisJoke():Flow<List<JokesDBEntity>>

    @Update
    suspend fun updateChuckNorrisJoke(entity: JokesDBEntity)

    @Query("SELECT EXISTS(SELECT * FROM CHUCK_NORRIS_TABLE WHERE id = :id)")
    fun isExists(id : String) : Boolean

    @Delete
    fun deleteChuckNorrisJoke(entity:JokesDBEntity)

}
