package com.equipo8.picobotella.ui.retos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.equipo8.picobotella.data.repository.RetoRepository
import com.equipo8.picobotella.model.Reto
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de retos.
 * Pertenece a la HU: Gestión de Retos.
 */
class RetosViewModel(private val repository: RetoRepository) : ViewModel() {

    val listaDeRetos = repository.todosLosRetos.asLiveData()

    fun agregarReto(descripcion: String) {
        viewModelScope.launch {
            repository.insertar(Reto(descripcion = descripcion))
        }
    }

    fun editarReto(reto: Reto) {
        viewModelScope.launch {
            repository.actualizar(reto)
        }
    }

    fun eliminarReto(reto: Reto) {
        viewModelScope.launch {
            repository.eliminar(reto)
        }
    }
}
