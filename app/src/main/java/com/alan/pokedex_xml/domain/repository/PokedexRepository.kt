package com.alan.pokedex_xml.domain.repository

import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon

interface PokedexRepository {

    suspend fun getPokemonList(): CallResult<List<Pokemon>>
}
