package com.alan.pokedex_xml.data.remote

import com.alan.pokedex_xml.data.remote.dto.PokemonListDto
import retrofit2.Response
import retrofit2.http.GET

interface PokedexService {

    @GET("pokemon/")
    suspend fun getPokemonList(): Response<PokemonListDto>
}
