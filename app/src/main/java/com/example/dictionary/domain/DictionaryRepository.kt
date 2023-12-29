package com.example.dictionary.domain

import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.util.Result

interface DictionaryRepository {

    suspend fun getWordDefinitions(word: String): Result<WordDetailsModel>
}