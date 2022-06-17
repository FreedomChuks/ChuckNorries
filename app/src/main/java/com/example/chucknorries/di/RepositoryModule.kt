package com.example.chucknorries.di


import com.example.chucknorries.data.ChuckNorrisRepository
import com.example.chucknorries.data.ChuckNorrisRepositoryContract
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
    abstract fun provideAstronomyRepository(repository: ChuckNorrisRepository): ChuckNorrisRepositoryContract

    @Binds
    abstract fun provideCacheDataSource(dataSource: CacheDataSource): CacheDataSourceContract

}