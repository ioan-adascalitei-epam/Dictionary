package com.example.dictionary.main

import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.data.model.MeaningResponse

data class DictionaryState(
    val state: UiState = UiState.Empty,
    val word: String = ""
)

sealed class UiState {
    data class Success(
        val audioPath: String? = null,
        val meanings: List<MeaningResponse> = emptyList(),
        val origin: String = ""
    ): UiState()

    data class Error(
        val error: ErrorInfo
    ): UiState()

    data object Loading : UiState()

    data object Empty : UiState()
}


