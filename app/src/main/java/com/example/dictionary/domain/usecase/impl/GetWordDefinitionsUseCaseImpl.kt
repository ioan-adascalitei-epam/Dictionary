package com.example.dictionary.domain.usecase.impl

import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.util.Result

class GetWordDefinitionsUseCaseImpl(
    private val repository: DictionaryRepository
): GetWordDefinitionsUseCase {

   override suspend fun getWordDefinitions(word: String): Result<WordDetailsModel> =
        repository.getWordDefinitions(word)
}