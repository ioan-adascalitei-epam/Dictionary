package com.example.dictionary.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dictionary.R
import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.main.UiState
import com.example.dictionary.main.UserInteraction

@Composable
fun DictionaryScreenSuccess(
    successState: UiState.Success,
    events: (UserInteraction) -> Unit
) {
    if (successState.audioPath.isNullOrEmpty().not()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        AudioPath(onPlayClicked = {
            events(UserInteraction.Listen)
        })
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }

    LazyColumn {
        items(successState.meanings) { meaning ->
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Meaning(
                modifier = Modifier,
                partOfSpeech = meaning.partOfSpeech,
                definitions = meaning.definitions
            )
        }
    }
}

@Composable
fun DictionaryScreenError(
    state: UiState.Error,
    events: (UserInteraction) -> Unit
) {
    when (val err = state.error) {
        is ErrorInfo.NotFound -> {
            GenericErrorView(
                icon = painterResource(id = R.drawable.ic_not_found),
                errorMsg = err.msg
            )
        }

        is ErrorInfo.GenericInfo -> {
            GenericErrorView(
                icon = painterResource(id = R.drawable.ic_generic_error),
                errorMsg = err.msg
            )
        }

        is ErrorInfo.NoInternet -> {
            NoInternetErrorView(
                onRetry = {
                    events(
                        UserInteraction.Retry
                    )
                }
            )
        }
    }
}