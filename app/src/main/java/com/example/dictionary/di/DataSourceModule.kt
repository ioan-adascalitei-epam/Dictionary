package com.example.dictionary.di

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.local.LocalDataSourceImpl
import com.example.dictionary.data.datasource.remote.RemoteDataSourceImpl
import com.example.dictionary.data.datasource.remote.api.DictionaryApi
import com.example.dictionary.data.datasource.repository.DictionaryRepositoryImpl
import com.example.dictionary.domain.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @RemoteDS
    abstract fun provideRemoteDS(remote: RemoteDataSourceImpl): DictionaryDataSource

    @Binds
    @LocalDS
    abstract fun provideLocalDS(local: LocalDataSourceImpl): DictionaryDataSource

}