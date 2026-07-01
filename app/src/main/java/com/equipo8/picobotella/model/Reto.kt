package com.equipo8.picobotella.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa un reto en la base de datos.
 * Pertenece a la HU: Gestión de Retos.
 */
@Entity(tableName = "retos")
data class Reto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descripcion: String
)
