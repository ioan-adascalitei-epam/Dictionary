package com.example.dictionary.data.datasource.remote

import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.remote.api.DictionaryApi
import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.util.Result

class RemoteDataSourceImpl(
    private val api: DictionaryApi
) : DictionaryDataSource {
    override suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>> =
        apiCall { api.getWordDefinitions(word) }

}