package com.example.dictionary.domain

import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.domain.model.WordDetailsModel

object WordDomainMapper {

    fun toWordDetails(wordDefinitionsResponse: WordDefinitionsResponse?) = WordDetailsModel(
        word = wordDefinitionsResponse?.word.orEmpty(),
        meanings = wordDefinitionsResponse?.meanings.orEmpty(),
        origin = wordDefinitionsResponse?.origin,
        audioPath = wordDefinitionsResponse?.phonetics?.firstOrNull()?.audio
    )
}