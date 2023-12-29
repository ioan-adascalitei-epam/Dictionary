package com.example.dictionary.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(DictionaryState())
    val uiState: StateFlow<DictionaryState> = _uiState

    fun handleUserInteraction(event: UserInteraction) {
        _uiState.value = _uiState.value.copy(state = UiState.Loading)
        when (event) {
            is UserInteraction.Search -> {
                _uiState.value = _uiState.value.copy(word = event.word)
                searchWord(event.word)
            }

            is UserInteraction.Listen -> {
            }

            UserInteraction.Retry -> {
                searchWord(_uiState.value.word)
            }
        }
    }

    private fun searchWord(word: String) {
        viewModelScope.launch {
            when (val wordDefinition =
                getWordDefinitionsUseCase.getWordDefinitions(word)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        state = UiState.Success(
                            wordDefinition.info.audioPath,
                            wordDefinition.info.meanings,
                            wordDefinition.info.origin.orEmpty()
                        )
                    )
                }

                is Result.Error -> {
                    _uiState.value =
                        _uiState.value.copy(state = UiState.Error(wordDefinition.errorInfo!!))
                }
            }
        }
    }
}