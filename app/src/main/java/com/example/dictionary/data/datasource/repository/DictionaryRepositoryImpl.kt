package com.example.dictionary.data.datasource.repository

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.util.Result
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val localDS: DictionaryDataSource,
    private val remoteDS: DictionaryDataSource,
    ) : DictionaryRepository {

    override suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>> {
        var result = localDS.getWordDefinitions(word)
        if(result is Result.Error) {
            result = remoteDS.getWordDefinitions(word)
            localDS.saveWordDefinition(result.data?.firstOrNull())
        }
        return result
    }
}