package com.example.chucknorries.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
//
//    @Binds
//    abstract fun provideAstronomyRepository(repository: AstronomyRepository): AstronomyRepositoryContract
//
//    @Binds
//    abstract fun provideCacheDataSource(dataSource: CacheDataSource): CacheDataSourceContract

}