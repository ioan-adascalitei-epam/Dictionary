package com.example.dictionary.domain.usecase

interface PlayAudioUseCase {

    fun playAudio(audioPath: String, onError: () -> Unit)
}