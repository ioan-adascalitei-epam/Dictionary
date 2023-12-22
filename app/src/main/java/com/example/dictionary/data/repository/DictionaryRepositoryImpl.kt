package com.example.dictionary.data.repository

import com.example.dictionary.data.api.DictionaryApi
import com.example.dictionary.data.model.ErrorInfo
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.model.WordDetails
import com.example.dictionary.util.Resource
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
) : DictionaryRepository {

    override suspend fun getWordDefinitions(word: String): Resource<WordDetails> {
        return try {
            val response = api.getWordDefinitions(word).first()
            Resource.Success(
                WordDetails(
                    word,
                    response.meanings,
                    response.origin,
                    response.phonetics.first().audio
                )
            )
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {
                    Resource.Error(ErrorInfo.NoInternet(e.message.toString()))
                }

                is HttpException -> {
                    if (e.code() == 404) {
                        Resource.Error(ErrorInfo.NotFound(e.message.toString()))
                    } else {
                        Resource.Error(ErrorInfo.GenericInfo(e.message.toString()))
                    }
                }

                else -> Resource.Error(ErrorInfo.GenericInfo(e.message.toString()))
            }
        }
    }
}