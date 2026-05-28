package com.equipo8.picobotella.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.equipo8.picobotella.data.repository.RetoRepository

/**
 * ViewModel para el Home.
 * Pertenece a la HU: Juego de la Botella.
 */
class HomeViewModel(private val repository: RetoRepository) : ViewModel() {

    private val _isAudioEnabled = MutableLiveData<Boolean>(true)
    val isAudioEnabled: LiveData<Boolean> get() = _isAudioEnabled

    private val _contadorRegresivo = MutableLiveData<Int>()
    val contadorRegresivo: LiveData<Int> get() = _contadorRegresivo

    fun toggleAudio() {
        _isAudioEnabled.value = !(_isAudioEnabled.value ?: true)
    }

    fun iniciarContador() {
        // Lógica del contador regresivo para el giro de la botella
    }
}
