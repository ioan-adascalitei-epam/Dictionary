package com.example.dictionary.domain.audio

import android.app.Application
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.exoplayer.ExoPlayer

class AudioWrapper(private val context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying.not() && player.playbackState == STATE_ENDED) {
                    player.release()
                }
            }
        })
    }

    fun playAudio(audioPath: String) {
        val mediaItem = MediaItem.fromUri(audioPath)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
}