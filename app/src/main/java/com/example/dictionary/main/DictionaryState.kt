package com.example.dictionary.main

import com.example.dictionary.domain.model.ErrorInfo

data class DictionaryState(
    val uiState: UiState = UiState.Empty,
    val word: String = ""
)

sealed class UiState {
    data class Success(
        val audioPath: String? = null,
        val meanings: List<MeaningState> = emptyList(),
        val origin: String = ""
    ): UiState()

    data class Error(
        val error: ErrorInfo
    ): UiState()

    data object Loading : UiState()

    data object Empty : UiState()
}

data class MeaningState(
    val partOfSpeech: String,
    val definitions: List<DefinitionState>
)

data class DefinitionState(
    val antonyms: List<String>?,
    val definition: String,
    val example: String?,
    val synonyms: List<String>?
)


