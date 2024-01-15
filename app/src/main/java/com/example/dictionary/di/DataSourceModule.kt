package com.example.dictionary.di

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.local.LocalDataSourceImpl
import com.example.dictionary.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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