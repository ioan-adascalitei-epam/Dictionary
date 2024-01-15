package com.example.dictionary.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dictionary.main.components.DictionaryScreenError
import com.example.dictionary.main.components.DictionaryScreenSuccess
import com.example.dictionary.main.components.LoadingView
import com.example.dictionary.main.components.WordToDefine
import com.example.dictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun DictionaryScreen(viewModel: MainViewModel) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DictionaryScreenComponent(
        state = state,
        events = viewModel::handleUserInteraction
    )
}

@Composable
fun DictionaryScreenComponent(
    state: DictionaryState,
    events: (UserInteraction) -> Unit
) {
    DictionaryTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(Modifier.padding(20.dp)) {
                WordToDefine(
                    modifier = Modifier,
                    word = state.word,
                    onWordUpdate = {
                        events(UserInteraction.WordUpdate(it))
                    },
                    onSearchClick = {
                        events(UserInteraction.Search(it))
                    }
                )

                when (state.uiState) {
                    is UiState.Success -> {
                        DictionaryScreenSuccess(successState = state.uiState, events = events)
                    }

                    is UiState.Error -> {
                        DictionaryScreenError(state = state.uiState, events = events)
                    }

                    UiState.Loading -> {
                        LoadingView()
                    }

                    UiState.Empty -> Unit
                }
            }
        }
    }
}



