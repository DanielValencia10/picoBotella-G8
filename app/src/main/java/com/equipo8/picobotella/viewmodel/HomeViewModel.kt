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
 * Usa AndroidViewModel para acceder al contexto y crear el repositorio sin necesidad de una Factory.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RetoRepository = RetoRepository(application)

    // Control del estado del audio (Habilitado/Deshabilitado)
    private val _isAudioEnabled = MutableLiveData<Boolean>(true)
    val isAudioEnabled: LiveData<Boolean> get() = _isAudioEnabled

    private val _contadorRegresivo = MutableLiveData<Int>()
    val contadorRegresivo: LiveData<Int> get() = _contadorRegresivo

    // Indica si el juego está en progreso (para ocultar botón y pausar audio)
    private val _isJugando = MutableLiveData<Boolean>(false)
    val isJugando: LiveData<Boolean> get() = _isJugando

    // Reto aleatorio seleccionado para mostrar en el diálogo
    private val _retoAleatorio = MutableLiveData<Reto?>()
    val retoAleatorio: LiveData<Reto?> get() = _retoAleatorio

    // Flag para saber si el audio estaba encendido antes de empezar el juego
    private var audioEstabaEncendido = true

    // Indica si no hay retos disponibles
    private val _sinRetos = MutableLiveData<Boolean>(false)
    val sinRetos: LiveData<Boolean> get() = _sinRetos

    fun toggleAudio() {
        _isAudioEnabled.value = !(_isAudioEnabled.value ?: true)
    }

    /**
     * Inicia el contador regresivo de 3 a 0, obtiene un reto aleatorio y controla el estado del juego.
     * Corresponde a HU 11 (Giro de botella) y HU 12 (Mostrar reto aleatorio).
     */
    fun iniciarJuego() {
        if (_isJugando.value == true) return  // No permitir múltiples juegos simultáneos

        _isJugando.value = true
        _sinRetos.value = false
        audioEstabaEncendido = _isAudioEnabled.value ?: true

        viewModelScope.launch {
            // Contador regresivo de 3 a 0
            for (i in 3 downTo 0) {
                _contadorRegresivo.value = i
                delay(1000)
            }

            // Obtener reto aleatorio de la base de datos
            val reto = repository.obtenerRetoAleatorio()
            _retoAleatorio.value = reto

            if (reto == null) {
                _sinRetos.value = true
            }

            // El juego termina, se habilita el botón
            _isJugando.value = false
        }
    }

    /**
     * Restaura el estado de audio después de cerrar el diálogo del reto (HU 11 C8).
     */
    fun restaurarAudio() {
        _isAudioEnabled.value = audioEstabaEncendido
    }

    /**
     * Limpia el reto aleatorio para evitar que se muestre de nuevo.
     * Se llama desde el Fragment después de mostrar el diálogo.
     */
    fun limpiarRetoAleatorio() {
        _retoAleatorio.value = null
    }
}