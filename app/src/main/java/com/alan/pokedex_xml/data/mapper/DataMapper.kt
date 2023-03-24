package com.alan.pokedex_xml.data.mapper

import com.alan.pokedex_xml.data.remote.dto.PokemonListDto
import com.alan.pokedex_xml.domain.model.Pokemon

object DataMapper {

    fun PokemonListDto.toPokemonList(): List<Pokemon> {
        return results.map { pokemon ->
            Pokemon(
                name = pokemon.name,
                url = pokemon.url
            )
        }
    }
}
