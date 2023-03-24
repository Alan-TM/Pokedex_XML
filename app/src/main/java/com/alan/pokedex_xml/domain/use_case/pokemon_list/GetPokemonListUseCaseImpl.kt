package com.alan.pokedex_xml.domain.use_case.pokemon_list

import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon
import com.alan.pokedex_xml.domain.repository.PokedexRepository

class GetPokemonListUseCaseImpl(
    private val repository: PokedexRepository
) : GetPokemonListUseCase {

    override suspend fun getPokemonList(): CallResult<List<Pokemon>> = repository.getPokemonList()
}
