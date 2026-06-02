package com.equipo8.picobotella.data.repository

import com.equipo8.picobotella.data.local.RetoDao
import com.equipo8.picobotella.model.Reto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Repositorio para gestionar los datos de los retos.
 * Pertenece a la HU: Gestión de Retos.
 */
class RetoRepository(private val retoDao: RetoDao? = null) {
    val todosLosRetos: Flow<List<Reto>> = retoDao?.obtenerTodos() ?: flowOf(emptyList())

    suspend fun insertar(reto: Reto) {
        retoDao?.insertar(reto)
    }

    suspend fun actualizar(reto: Reto) {
        retoDao?.actualizar(reto)
    }

    suspend fun eliminar(reto: Reto) {
        retoDao?.eliminar(reto)
    }

    suspend fun obtenerRetoAleatorio(): Reto? {
        return retoDao?.obtenerAleatorio()
    }
}
