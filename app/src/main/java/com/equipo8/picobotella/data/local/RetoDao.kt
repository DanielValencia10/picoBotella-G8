package com.equipo8.picobotella.data.local

import androidx.room.*
import com.equipo8.picobotella.model.Reto
import kotlinx.coroutines.flow.Flow

/**
 * DAO para las operaciones de la entidad Reto.
 * Pertenece a la HU: Gestión de Retos.
 */
@Dao
interface RetoDao {
    @Insert
    suspend fun insertar(reto: Reto)

    @Update
    suspend fun actualizar(reto: Reto)

    @Delete
    suspend fun eliminar(reto: Reto)

    @Query("SELECT * FROM retos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<Reto>>

    @Query("SELECT * FROM retos ORDER BY RANDOM() LIMIT 1")
    suspend fun obtenerAleatorio(): Reto?
}
