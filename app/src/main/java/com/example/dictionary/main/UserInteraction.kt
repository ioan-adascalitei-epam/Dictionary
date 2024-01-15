package com.example.dictionary.main

sealed interface UserInteraction {

    data class Search(val word: String) : UserInteraction
    data class WordUpdate(val word: String) : UserInteraction
    data object Listen : UserInteraction
    data object Retry : UserInteraction
}