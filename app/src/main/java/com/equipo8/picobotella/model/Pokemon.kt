package com.equipo8.picobotella.model

/**
 * Modelo para un Pokemon según la API de Pokémon GO Pokedex.
 * Pertenece a la HU: Consumo de API Pokemon.
 */
data class Pokemon(
    val id: Int,
    val num: String,
    val name: String,
    val img: String,
    val type: List<String>? = null,
    val height: String? = null,
    val weight: String? = null,
    val candy: String? = null,
    val candy_count: Int? = null,
    val egg: String? = null,
    val spawn_chance: Double? = null,
    val avg_spawns: Double? = null,
    val spawn_time: String? = null,
    val multipliers: List<Double>? = null,
    val weaknesses: List<String>? = null,
    val next_evolution: List<Evolution>? = null,
    val prev_evolution: List<Evolution>? = null
)

data class Evolution(
    val num: String,
    val name: String
)