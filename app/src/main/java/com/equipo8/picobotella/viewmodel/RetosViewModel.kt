package com.equipo8.picobotella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.equipo8.picobotella.model.Reto
import com.equipo8.picobotella.repository.RetoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de retos.
 * Usa AndroidViewModel para acceder al contexto y crear el repositorio directamente.
 */
class RetosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RetoRepository = RetoRepository(application)
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
