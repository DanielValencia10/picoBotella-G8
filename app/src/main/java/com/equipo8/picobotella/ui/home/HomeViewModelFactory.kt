package com.equipo8.picobotella.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.equipo8.picobotella.data.repository.RetoRepository

/**
 * Fábrica para instanciar el HomeViewModel inyectándole su repositorio.
 * Pertenece a la HU2: Ventana Home Principal
 */
class HomeViewModelFactory(private val repository: RetoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}