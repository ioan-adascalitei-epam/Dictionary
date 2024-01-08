package com.example.dictionary.data.datasource

import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.util.Result

interface DictionaryDataSource {

    suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>>

    suspend fun saveWordDefinition(wordDefinition: WordDefinitionsResponse)

    suspend fun deleteWordDefinitions(words: List<String>)
}