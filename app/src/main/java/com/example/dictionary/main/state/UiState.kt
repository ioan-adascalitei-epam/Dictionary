package com.example.dictionary.main.state

import com.example.dictionary.data.model.ErrorInfo
import com.example.dictionary.data.model.Meaning

sealed class UiState {
    data class Success(
        val audioPath: String? = null,
        val meanings: List<Meaning> = emptyList(),
        val origin: String = ""
    ): UiState()

    data class Error(
        val error: ErrorInfo
    ): UiState()

    data object Loading : UiState()

    data object Empty : UiState()
}


