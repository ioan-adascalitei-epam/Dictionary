package com.example.dictionary.data.datasource.repository

import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.util.Result

interface DictionaryRepository {

    suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>>
}