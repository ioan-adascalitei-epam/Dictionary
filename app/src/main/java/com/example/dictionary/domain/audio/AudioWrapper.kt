package com.example.dictionary.domain.audio

import android.media.AudioAttributes
import android.media.MediaPlayer

class AudioWrapper {

    fun playAudio(audioPath: String, onError: () -> Unit) {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(audioPath)
            prepareAsync()
            setOnPreparedListener {
                start()
            }
            setOnCompletionListener {
                release()
            }
            setOnErrorListener { _, _, _ ->
                onError()
                release()
                false
            }
        }
    }
}