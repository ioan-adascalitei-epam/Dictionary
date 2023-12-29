package com.example.dictionary.di

import com.example.dictionary.BuildConfig
import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.remote.RemoteDataSourceImpl
import com.example.dictionary.data.datasource.remote.api.DictionaryApi
import com.example.dictionary.data.datasource.repository.DictionaryRepositoryImpl
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.domain.usecase.impl.GetWordDefinitionsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi =
        retrofit.create(DictionaryApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDS(api: DictionaryApi): DictionaryDataSource = RemoteDataSourceImpl(api)

 /*   @Singleton
    @Provides
    fun provideLocalDS(): DictionaryDataSource = LocalDataSourceImpl()
*/
    @Singleton
    @Provides
    fun provideDictionaryRepository(
     localDS: DictionaryDataSource,
     remoteDs: DictionaryDataSource
    ): DictionaryRepository =
        DictionaryRepositoryImpl(localDS, remoteDs)

    @Singleton
    @Provides
    fun provideGetWordDefinitionsUseCase(repository: DictionaryRepository): GetWordDefinitionsUseCase =
        GetWordDefinitionsUseCaseImpl(repository)

}