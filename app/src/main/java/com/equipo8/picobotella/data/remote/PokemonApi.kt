package com.equipo8.picobotella.data.remote

import retrofit2.http.GET

/**
 * Interfaz para el consumo de la API de Pokemon.
 * Pertenece a la HU: Consumo de API Pokemon.
 */
interface PokemonApi {
    @GET("Biuni/PokemonGO-Pokedex/master/pokedex.json")
    suspend fun getPokemons(): PokemonResponse

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
