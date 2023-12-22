package com.example.dictionary.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dictionary.R
import com.example.dictionary.data.model.Definition
import com.example.dictionary.data.model.ErrorInfo
import com.example.dictionary.main.state.UiState
import com.example.dictionary.main.state.UserInteraction
import com.example.dictionary.main.viewmodel.MainViewModel
import com.example.dictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.padding(20.dp)) {
                        WordToDefine(
                            modifier = Modifier,
                            currentWord = viewModel.word,
                            onWordChange = viewModel::updateWord,
                            onSearchClick = {
                                viewModel.handleUserInteraction(UserInteraction.Search)
                                Log.v("test_s", "icon click passed")
                            }
                        )

                        val uiState by viewModel.uiState.collectAsState()
                        when (uiState) {
                            is UiState.Success -> {
                                val successResponse = uiState as UiState.Success
                                if (successResponse.audioPath.isNullOrEmpty().not()) {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                    )
                                    AudioPath(onPlayClicked = {
                                        viewModel.handleUserInteraction(
                                            UserInteraction.Listen
                                        )
                                    })
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                    )
                                }

                                LazyColumn {
                                    items(successResponse.meanings) { meaning ->
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

                            is UiState.Error -> {
                                when (val err = (uiState as UiState.Error).error) {
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
                                                viewModel.handleUserInteraction(
                                                    UserInteraction.Search
                                                )
                                            }
                                        )
                                    }
                                }
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
    }
}

@Composable
fun WordToDefine(
    modifier: Modifier = Modifier,
    currentWord: String = "",
    onWordChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    TextField(
        value = currentWord,
        onValueChange = { input -> onWordChange(input) },
        label = { Text(text = stringResource(id = R.string.word_to_define_label)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        trailingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(id = R.string.search_content_description),
                modifier.clickable { onSearchClick() })
        }
    )
}

@Composable
fun AudioPath(
    modifier: Modifier = Modifier,
    onPlayClicked: () -> Unit = {}
) {
    Card(modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_play),
                contentDescription = stringResource(id = R.string.play_content_description),
                modifier
                    .clickable { onPlayClicked() }
                    .size(60.dp)
                    .scale(0.8f)
                    .padding(8.dp))
            Text(
                text = stringResource(id = R.string.audio_msg),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun CardInfo(
    modifier: Modifier = Modifier,
    keyName: String,
    value: String
) {
    Card(modifier.fillMaxWidth()) {
        Column(modifier.padding(8.dp)) {
            Text(
                text = keyName,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun Definition(
    modifier: Modifier = Modifier,
    definition: String,
    example: String?,
    synonyms: List<String>?,
    antonyms: List<String>?
) {
    Card(modifier.fillMaxWidth()) {
        Column(modifier.padding(8.dp)) {
            CardInfo(keyName = "Definition", value = definition)
            if (example.isNullOrEmpty().not()) {
                CardInfo(keyName = "Example", value = example!!)
            }
            if (synonyms.isNullOrEmpty().not()) {
                CardInfo(keyName = "Synonyms", value = synonyms!!.joinToString { "$it\t" })
            }
            if (antonyms.isNullOrEmpty().not()) {
                CardInfo(keyName = "Antonyms", value = antonyms!!.joinToString { "$it\t" })
            }
        }
    }
}

@Composable
fun Meaning(
    modifier: Modifier = Modifier,
    partOfSpeech: String = "",
    definitions: List<Definition> = emptyList()
) {
    Card(modifier.fillMaxWidth()) {
        Column {
            Text(
                text = stringResource(id = R.string.part_of_speech, partOfSpeech),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(8.dp)
            )
            definitions.forEach { definition ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Definition(
                    modifier = modifier,
                    definition = definition.definition,
                    example = definition.example,
                    synonyms = definition.synonyms,
                    antonyms = definition.antonyms
                )
            }
        }
    }
}

@Composable
fun GenericErrorView(
    modifier: Modifier = Modifier,
    errorMsg: String = "",
    icon: Painter = painterResource(id = R.drawable.ic_generic_error)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(id = R.string.error_icon_description),
            modifier = modifier
                .scale(0.6f)
                .size(100.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Text(
            text = errorMsg,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(8.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
    }
}

@Composable
fun NoInternetErrorView(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GenericErrorView(
            icon = painterResource(id = R.drawable.ic_no_internet),
            errorMsg = stringResource(id = R.string.no_internet),
            modifier = modifier
        )
        Button(onClick = { onRetry() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun WordToDefinePreview() {
    DictionaryTheme {
        WordToDefine()
    }
}

@Preview(showBackground = true)
@Composable
fun AudioPathPreview() {
    DictionaryTheme {
        AudioPath()
    }
}

@Preview(showBackground = true)
@Composable
fun CardInfoPreview() {
    DictionaryTheme {
        CardInfo(keyName = "Part of speech", value = "Noun\tverb\nsynonim")
    }
}

@Preview(showBackground = true)
@Composable
fun DefinitionPreview() {
    DictionaryTheme {
        Definition(
            definition = "Lorem ipsum",
            example = "bla bla lbdefwregfr fwrefg",
            synonyms = listOf("ala", "bala", "portocala", "dam", "tram"),
            antonyms = emptyList()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenericErrorViewPreview() {
    DictionaryTheme {
        GenericErrorView()
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetErrorViewPreview() {
    DictionaryTheme {
        NoInternetErrorView()
    }
}