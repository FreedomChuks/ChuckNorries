package com.example.chucknorries.di

import android.app.Application
import androidx.room.Room
import com.example.chucknorries.data.local.AppDatabase
import com.example.chucknorries.data.local.dao.ChuckNorrisDAO
import com.example.chucknorries.domain.utils.Constant.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesCharacterDao(appDatabase: AppDatabase): ChuckNorrisDAO {
        return appDatabase.chuckNorrisDAO()
    }
}