package com.equipo8.picobotella.repository

import android.content.Context
import com.equipo8.picobotella.data.local.AppDatabase
import com.equipo8.picobotella.data.local.RetoDao
import com.equipo8.picobotella.model.Reto
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para gestionar los datos de los retos.
 * El repositorio es el puente entre el DAO y el ViewModel.
 */
class RetoRepository(context: Context) {
    private val retoDao: RetoDao = AppDatabase.getDatabase(context).retoDao()
    
    val todosLosRetos: Flow<List<Reto>> = retoDao.obtenerTodos()

    suspend fun insertar(reto: Reto) {
        retoDao.insertar(reto)
    }

    suspend fun actualizar(reto: Reto) {
        retoDao.actualizar(reto)
    }

    suspend fun eliminar(reto: Reto) {
        retoDao.eliminar(reto)
    }

    suspend fun obtenerRetoAleatorio(): Reto? {
        return retoDao.obtenerAleatorio()
    }
}
