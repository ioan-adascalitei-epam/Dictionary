package com.example.dictionary.domain

import com.example.dictionary.domain.model.WordDetails
import com.example.dictionary.util.Resource

interface DictionaryRepository {

    suspend fun getWordDefinitions(word: String): Resource<WordDetails>
}