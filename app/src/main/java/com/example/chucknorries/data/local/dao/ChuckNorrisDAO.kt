package com.example.chucknorries.data.local.dao

import androidx.room.*
import com.example.chucknorries.data.local.entities.ChuckNorrisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChuckNorrisDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChuckNorrisJoke(entity: ChuckNorrisEntity)

    @Query("SELECT * FROM CHUCK_NORRIS_TABLE")
    fun getChuckNorrisJoke():Flow<List<ChuckNorrisEntity>>

    @Update
    suspend fun updateChuckNorrisJoke(entity: ChuckNorrisEntity)


}
