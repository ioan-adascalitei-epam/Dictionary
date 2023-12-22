package com.example.dictionary.domain.usecase.impl

import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.model.WordDetails
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.util.Resource

class GetWordDefinitionsUseCaseImpl(
    private val repository: DictionaryRepository
): GetWordDefinitionsUseCase {

   override suspend fun getWordDefinitions(word: String): Resource<WordDetails> =
        repository.getWordDefinitions(word)

}