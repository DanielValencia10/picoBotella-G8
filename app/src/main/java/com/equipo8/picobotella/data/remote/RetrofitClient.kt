package com.equipo8.picobotella.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente singleton de Retrofit.
 * Pertenece a la HU: Consumo de API Pokemon.
 */
object RetrofitClient {
    private const val BASE_URL = "https://raw.githubusercontent.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokemonApi: PokemonApi by lazy {
        retrofit.create(PokemonApi::class.java)
    }
}