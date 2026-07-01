package com.equipo8.picobotella.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.equipo8.picobotella.data.repository.RetoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de negocio y la gestión de estados de la pantalla principal
 * Pertenece a la HU2: Ventana Home Principal
 */
class HomeViewModel(private val repository: RetoRepository) : ViewModel() {

    // Control del estado del audio (Habilitado/Deshabilitado)
    private val _isAudioEnabled = MutableLiveData<Boolean>(true)
    val isAudioEnabled: LiveData<Boolean> get() = _isAudioEnabled

    private val _contadorRegresivo = MutableLiveData<Int>()
    val contadorRegresivo: LiveData<Int> get() = _contadorRegresivo

    fun toggleAudio() {
        _isAudioEnabled.value = !(_isAudioEnabled.value ?: true)
    }

    /**
     * Inicia el contador regresivo de 3 a 0
    */
    fun iniciarContador() {
        viewModelScope.launch {
            for (i in 3 downTo 0) {
                _contadorRegresivo.value = i
                delay(1000) // Pausa asíncrona de 1 segundo
            }
        }
    }
}