package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.util.Result

interface GetWordDefinitionsUseCase {

    suspend fun getWordDefinitions(word: String): Result<WordDetailsModel>
}