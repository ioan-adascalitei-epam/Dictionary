package com.example.dictionary.main.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.ErrorInfo
import com.example.dictionary.domain.usecase.GetWordDefinitionsUseCase
import com.example.dictionary.main.state.UiState
import com.example.dictionary.main.state.UserInteraction
import com.example.dictionary.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase
) : ViewModel() {

    var word by mutableStateOf("")
        private set

    fun updateWord(input: String) {
        word = input
    }

    private var _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun handleUserInteraction(event: UserInteraction) {
        _uiState.value = UiState.Loading
        when (event) {
            is UserInteraction.Search -> {
                Log.v("user_interaction", "Search")
                viewModelScope.launch {
                    when(val wordDefinition = getWordDefinitionsUseCase.getWordDefinitions(word)) {
                        is Resource.Success -> {
                            _uiState.value = UiState.Success(
                                wordDefinition.info.audioPath,
                                wordDefinition.info.meanings,
                                wordDefinition.info.origin.orEmpty()
                            )
                        }
                        is Resource.Error -> {
                            _uiState.value = UiState.Error(wordDefinition.errorInfo!!)
                        }
                    }
                }
            }

            is UserInteraction.Listen -> {
                Log.v("user_interaction", "Listen")
            }
        }
    }
}