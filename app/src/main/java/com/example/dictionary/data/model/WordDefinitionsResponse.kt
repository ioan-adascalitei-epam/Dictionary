package com.example.dictionary.data.model

data class WordDefinitionsResponse(
    val meanings: List<MeaningResponse>,
    val origin: String?,
    val phonetic: String,
    val phonetics: List<PhoneticResponse>,
    val word: String
)

data class MeaningResponse(
    val definitions: List<DefinitionResponse>,
    val partOfSpeech: String
)

data class PhoneticResponse(
    val audio: String?,
    val text: String
)

data class DefinitionResponse(
    val antonyms: List<String>?,
    val definition: String,
    val example: String,
    val synonyms: List<String>?
)