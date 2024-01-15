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
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase,
    private val meaningMapper: MeaningStateMapper
) : ViewModel() {

    private var _uiState = MutableStateFlow(DictionaryState())
    val uiState: StateFlow<DictionaryState> = _uiState

    fun handleUserInteraction(event: UserInteraction) {
        when (event) {
            is UserInteraction.Search -> {
                _uiState.value = _uiState.value.copy(word = event.word, uiState = UiState.Loading)
                searchWord(event.word)
            }

            is UserInteraction.Listen -> {
            }

            is UserInteraction.Retry -> {
                searchWord(_uiState.value.word)
            }

            is UserInteraction.WordUpdate -> {
                _uiState.value = _uiState.value.copy(word = event.word)
            }
        }
    }

    private fun searchWord(word: String) {
        viewModelScope.launch {
            when (val wordDefinition =
                getWordDefinitionsUseCase.getWordDefinitions(word)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        uiState = UiState.Success(
                            wordDefinition.info.audioPath,
                            wordDefinition.info.meanings.map { meaningResponse ->
                                meaningMapper.toMeaningState(meaningResponse)
                            },
                            wordDefinition.info.origin.orEmpty()
                        )
                    )
                }

                is Result.Error -> {
                    _uiState.value =
                        _uiState.value.copy(uiState = UiState.Error(wordDefinition.errorInfo!!))
                }
            }
        }
    }
}