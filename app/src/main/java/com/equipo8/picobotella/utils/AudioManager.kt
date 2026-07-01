package com.equipo8.picobotella.utils

import android.content.Context
import android.media.MediaPlayer

/**
 * Singleton para manejar la reproducción de audio en la aplicación.
 * Pertenece a la HU: Efectos de Sonido.
 */
object AudioManager {
    private var mediaPlayer: MediaPlayer? = null

    fun play(context: Context, resId: Int) {
        stop()
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }
}
