package com.alan.pokedex_xml.domain.use_case.pokemon_list

import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon

interface GetPokemonListUseCase {

    suspend fun getPokemonList(): CallResult<List<Pokemon>>
}
