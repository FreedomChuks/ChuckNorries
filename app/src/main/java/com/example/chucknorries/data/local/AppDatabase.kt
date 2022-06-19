package com.example.chucknorries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chucknorries.data.local.dao.JokesDAO
import com.example.chucknorries.data.local.entities.JokesCacheEntity

@Database(version = 1,
entities = [JokesCacheEntity::class],
exportSchema = false)

@TypeConverters(
 ListTypeConverter::class
)
abstract class AppDatabase:RoomDatabase() {
   abstract fun chuckNorrisDAO(): JokesDAO
}