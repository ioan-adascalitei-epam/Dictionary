package com.example.dictionary.data.datasource.remote

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.remote.api.DictionaryApi
import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.di.RemoteDS
import com.example.dictionary.util.Result
import javax.inject.Inject

@RemoteDS
class RemoteDataSourceImpl @Inject constructor(
    private val api: DictionaryApi
) : DictionaryDataSource {
    override suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>> =
        apiCall { api.getWordDefinitions(word) }

    override suspend fun saveWordDefinition(wordDefinition: WordDefinitionsResponse) {
        throw NotImplementedError("No need for remote implementation")
    }

    override suspend fun deleteWordDefinitions(words: List<String>) {
        throw NotImplementedError("No need for remote implementation")
    }

}