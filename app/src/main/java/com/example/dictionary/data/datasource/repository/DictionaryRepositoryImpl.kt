package com.example.dictionary.data.datasource.repository

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.util.Result
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val localDS: DictionaryDataSource,
    private val remoteDS: DictionaryDataSource,

    ) : DictionaryRepository {

    override suspend fun getWordDefinitions(word: String): Result<WordDetailsModel> =
        when (val response = remoteDS.getWordDefinitions(word)) {
            is Result.Success -> {
                localDS.saveWordDefinition(response.info.first())
                Result.Success(
                    WordDetailsModel(
                        word,
                        response.info.first().meanings,
                        response.info.first().origin,
                        response.info.first().phonetics.first().audio
                    )
                )
            }

            is Result.Error -> {
                when (response.errorInfo) {
                    is ErrorInfo.NoInternet -> {
                        val savedResult = localDS.getWordDefinitions(word)
                        if (savedResult is Result.Success) {
                            Result.Success(
                                WordDetailsModel(
                                    word,
                                    savedResult.data!!.first().meanings,
                                    savedResult.data.first().origin,
                                    savedResult.data.first().phonetics.first().audio
                                )
                            )
                        } else {
                            Result.Error(response.errorInfo)
                        }
                    }

                    else -> Result.Error(response.errorInfo)
                }
            }
        }
}