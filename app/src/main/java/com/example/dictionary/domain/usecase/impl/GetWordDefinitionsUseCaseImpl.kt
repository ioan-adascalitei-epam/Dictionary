package com.example.dictionary.domain.usecase.impl

import com.example.dictionary.data.datasource.repository.DictionaryRepository
import com.example.dictionary.domain.WordDomainMapper
import com.example.dictionary.domain.model.WordDetailsModel
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.util.Result

class GetWordDefinitionsUseCaseImpl(
    private val repository: DictionaryRepository,
    private val mapper: WordDomainMapper
) : GetWordDefinitionsUseCase {

    override suspend fun getWordDefinitions(word: String): Result<WordDetailsModel> {
        val result = repository.getWordDefinitions(word)
        val domainModel = if (result is Result.Success) {
            Result.Success(mapper.toWordDetails(result.data?.firstOrNull()))
        } else Result.Error(result.error)
        return domainModel
    }

}