package com.example.dictionary.domain.usecase.impl

import com.example.dictionary.domain.audio.AudioWrapper
import com.example.dictionary.domain.usecase.PlayAudioUseCase

class PlayAudioUseCaseImpl(private val audioWrapper: AudioWrapper) : PlayAudioUseCase {
    override fun playAudio(audioPath: String, onError: () -> Unit) {
        audioWrapper.playAudio(audioPath, onError)
    }
}