package com.equipo8.picobotella.ui.retos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.equipo8.picobotella.data.repository.RetoRepository

class RetosViewModelFactory(private val repository: RetoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RetosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RetosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
