package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.model.WordDetails
import com.example.dictionary.util.Resource

interface GetWordDefinitionsUseCase {

    suspend fun getWordDefinitions(word: String): Resource<WordDetails>
}