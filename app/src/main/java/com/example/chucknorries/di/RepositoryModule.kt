package com.example.chucknorries.di


import com.example.chucknorries.data.JokesRepository
import com.example.chucknorries.data.JokesRepositoryContract
import com.example.chucknorries.data.datasource.cache.CacheDataSource
import com.example.chucknorries.data.datasource.cache.CacheDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAstronomyRepository(repository: JokesRepository): JokesRepositoryContract

    @Binds
    abstract fun provideCacheDataSource(dataSource: CacheDataSource): CacheDataSourceContract

}