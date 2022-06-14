package com.example.chucknorries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chucknorries.data.local.converter.ListConverter
import com.example.chucknorries.data.local.dao.ChuckNorrisDAO
import com.example.chucknorries.data.local.entities.ChuckNorrisEntity

@Database(version = 1,
entities = [ChuckNorrisEntity::class],
exportSchema = false)

@TypeConverters(
 ListConverter::class
)
abstract class AppDatabase:RoomDatabase() {
   abstract fun chuckNorrisDAO(): ChuckNorrisDAO
}