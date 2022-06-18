package com.example.chucknorries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chucknorries.data.local.converter.ListConverter
import com.example.chucknorries.data.local.dao.JokesDAO
import com.example.chucknorries.data.local.entities.JokesDBEntity

@Database(version = 1,
entities = [JokesDBEntity::class],
exportSchema = false)

@TypeConverters(
 ListConverter::class
)
abstract class AppDatabase:RoomDatabase() {
   abstract fun chuckNorrisDAO(): JokesDAO
}