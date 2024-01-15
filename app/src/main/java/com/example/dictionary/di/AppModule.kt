package com.example.dictionary.di

import android.content.Context
import androidx.room.Room
import com.example.dictionary.BuildConfig
import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.local.EntityMapper
import com.example.dictionary.data.datasource.local.WordsDatabase
import com.example.dictionary.data.datasource.remote.api.DictionaryApi
import com.example.dictionary.data.datasource.repository.DictionaryRepositoryImpl
import com.example.dictionary.data.datasource.repository.DictionaryRepository
import com.example.dictionary.domain.WordDomainMapper
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.domain.usecase.impl.GetWordDefinitionsUseCaseImpl
import com.example.dictionary.main.MeaningStateMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val DbName = "words_db"

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
    fun provideRoomDb(@ApplicationContext context: Context): WordsDatabase = Room.databaseBuilder(
        context,
        WordsDatabase::class.java, DbName
    ).build()

    @Singleton
    @Provides
    fun provideEntityMapper() = EntityMapper

    @Singleton
    @Provides
    fun provideWordDomainModelMapper() = WordDomainMapper

    @Singleton
    @Provides
    fun provideMeaningStateMapper() = MeaningStateMapper

    @Singleton
    @Provides
    fun provideDictionaryRepository(
        @LocalDS localDS: DictionaryDataSource,
        @RemoteDS remoteDs: DictionaryDataSource
    ): DictionaryRepository = DictionaryRepositoryImpl(localDS, remoteDs)

    @Singleton
    @Provides
    fun provideGetWordDefinitionsUseCase(
        repository: DictionaryRepository,
        wordMapper: WordDomainMapper
    ): GetWordDefinitionsUseCase =
        GetWordDefinitionsUseCaseImpl(repository, wordMapper)

}