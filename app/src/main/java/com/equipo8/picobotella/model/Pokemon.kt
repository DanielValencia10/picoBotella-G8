package com.equipo8.picobotella.model

/**
 * Modelo para un Pokemon.
 * Pertenece a la HU: Consumo de API Pokemon.
 */
data class Pokemon(
    val id: Int,
    val num: String,
    val name: String,
    val img: String
)
