package com.example.dictionary.di

import android.app.Application
import android.content.Context
import com.example.dictionary.BuildConfig
import com.example.dictionary.data.api.DictionaryApi
import com.example.dictionary.data.repository.DictionaryRepositoryImpl
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.audio.AudioWrapper
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.domain.usecase.PlayAudioUseCase
import com.example.dictionary.domain.usecase.impl.GetWordDefinitionsUseCaseImpl
import com.example.dictionary.domain.usecase.impl.PlayAudioUseCaseImpl
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
    fun provideDictionaryRepository(api: DictionaryApi): DictionaryRepository =
        DictionaryRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideGetWordDefinitionsUseCase(repository: DictionaryRepository): GetWordDefinitionsUseCase =
        GetWordDefinitionsUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideAudioWrapper(@ApplicationContext context: Context): AudioWrapper =
        AudioWrapper(context)

    @Singleton
    @Provides
    fun providePlayAudioUseCase(audioWrapper: AudioWrapper): PlayAudioUseCase =
        PlayAudioUseCaseImpl(audioWrapper)
}