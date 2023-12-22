package com.example.dictionary.main.state

sealed class UserInteraction {

    data object Search: UserInteraction()
    data object Listen: UserInteraction()
}