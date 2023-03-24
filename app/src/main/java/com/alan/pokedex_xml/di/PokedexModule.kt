package com.alan.pokedex_xml.di

import com.alan.pokedex_xml.data.remote.PokedexService
import com.alan.pokedex_xml.data.repository.PokedexRepositoryImpl
import com.alan.pokedex_xml.domain.repository.PokedexRepository
import com.alan.pokedex_xml.domain.use_case.pokemon_list.GetPokemonListUseCase
import com.alan.pokedex_xml.domain.use_case.pokemon_list.GetPokemonListUseCaseImpl
import com.alan.pokedex_xml.presentation.pokemon_list.viewmodel.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {
    // Mover retrofit a su propio koin module

    single<PokedexRepository> {
        PokedexRepositoryImpl(get<PokedexService>())
    }

    factory<GetPokemonListUseCase> {
        GetPokemonListUseCaseImpl(get<PokedexRepository>())
    }

    viewModel {
        PokemonListViewModel(get<GetPokemonListUseCase>())
    }
}
