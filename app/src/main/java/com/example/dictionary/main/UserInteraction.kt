package com.example.dictionary.main

sealed class UserInteraction {

    data class Search(val word: String): UserInteraction()
    data object Listen: UserInteraction()
    data object Retry: UserInteraction()
}