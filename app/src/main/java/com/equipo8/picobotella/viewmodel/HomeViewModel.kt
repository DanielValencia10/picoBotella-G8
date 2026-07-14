package com.equipo8.picobotella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equipo8.picobotella.model.Reto
import com.equipo8.picobotella.repository.RetoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de negocio y la gestión de estados de la pantalla principal.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RetoRepository = RetoRepository(application)

    private val _isAudioEnabled = MutableLiveData<Boolean>(true)
    val isAudioEnabled: LiveData<Boolean> get() = _isAudioEnabled

    private val _contadorRegresivo = MutableLiveData<Int>()
    val contadorRegresivo: LiveData<Int> get() = _contadorRegresivo

    private val _isJugando = MutableLiveData<Boolean>(false)
    val isJugando: LiveData<Boolean> get() = _isJugando

    private val _retoAleatorio = MutableLiveData<Reto?>()
    val retoAleatorio: LiveData<Reto?> get() = _retoAleatorio

    private val _sinRetos = MutableLiveData<Boolean>(false)
    val sinRetos: LiveData<Boolean> get() = _sinRetos

    private var audioEstabaEncendido = true

    fun toggleAudio() {
        _isAudioEnabled.value = !(_isAudioEnabled.value ?: true)
    }

    /**
     * Paso 1: Se llama cuando la botella empieza a girar.
     */
    fun iniciarPartida() {
        if (_isJugando.value == true) return
        _isJugando.value = true
        _sinRetos.value = false
        audioEstabaEncendido = _isAudioEnabled.value ?: true
        _isAudioEnabled.value = false 
    }

    /**
     * Paso 2: Se llama cuando la botella se detiene para mostrar el reto.
     */
    fun iniciarJuego() {
        viewModelScope.launch {
            // Cuenta regresiva rápida antes del reto
            for (i in 3 downTo 0) {
                _contadorRegresivo.value = i
                delay(800)
            }

            val reto = repository.obtenerRetoAleatorio()
            if (reto == null) {
                _sinRetos.value = true
                _isAudioEnabled.value = audioEstabaEncendido // Recuperar audio si no hay reto
            } else {
                _retoAleatorio.value = reto
            }
            _isJugando.value = false
        }
    }

    fun restaurarAudio() {
        _isAudioEnabled.value = audioEstabaEncendido
    }

    fun limpiarRetoAleatorio() {
        _retoAleatorio.value = null
    }
}
