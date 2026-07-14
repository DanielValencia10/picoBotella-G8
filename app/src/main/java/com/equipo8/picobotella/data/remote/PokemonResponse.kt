package com.equipo8.picobotella.data.remote

import com.equipo8.picobotella.model.Pokemon

/**
 * Respuesta de la API de Pokemon.
 * Pertenece a la HU: Consumo de API Pokemon.
 */
data class PokemonResponse(
    val pokemon: List<Pokemon>
)
