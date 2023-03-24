package com.alan.pokedex_xml.presentation.pokemon_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon
import com.alan.pokedex_xml.domain.repository.PokedexRepository
import com.alan.pokedex_xml.domain.use_case.pokemon_list.GetPokemonListUseCase
import com.cursosandroidant.historicalweatherref.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var repository: PokedexRepository

    @RelaxedMockK
    private lateinit var useCase: GetPokemonListUseCase

    private var pokemonListViewModel: PokemonListViewModel? = null
    private var pokemonList: List<Pokemon>? = null

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Default)
        pokemonList = listOf(
            Pokemon("charizard", "www.pokeapi.com/pokemon/charizard")
        )
        pokemonListViewModel = PokemonListViewModel(useCase)
    }

    @After
    fun tearDown() {
        pokemonListViewModel = null
        pokemonList = null
    }

    @Test
    fun `given successful api call when result is returned then returns CallResult with pokemon list`() {
        val expected = listOf(Pokemon("charizard", "www.pokeapi.com/pokemon/charizard"))
        val result = CallResult.Success(pokemonList!!)

        coEvery { useCase.getPokemonList() } returns result
        pokemonListViewModel!!.fetchPokemonList()

        assertTrue(result.data!!.isNotEmpty())
        assertEquals(pokemonListViewModel!!.pokemonList.getOrAwaitValue(), expected)
    }

    @Test
    fun `given successful api call when result returns an empty list then returns CallResult Success with empty list`() {
        val expected = emptyList<Pokemon>()
        val result = CallResult.Success(data = emptyList<Pokemon>())

        coEvery { useCase.getPokemonList() } returns result
        pokemonListViewModel!!.fetchPokemonList()

        assertTrue(result.data!!.isEmpty())
        assertEquals(pokemonListViewModel!!.pokemonList.getOrAwaitValue(), expected)
    }

    // TODO add error test cases
}
