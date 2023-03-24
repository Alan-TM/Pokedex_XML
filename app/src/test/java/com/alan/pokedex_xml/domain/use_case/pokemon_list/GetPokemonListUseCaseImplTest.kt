package com.alan.pokedex_xml.domain.use_case.pokemon_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon
import com.alan.pokedex_xml.domain.repository.PokedexRepository
import io.mockk.MockKAnnotations
import io.mockk.MockKStubScope
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPokemonListUseCaseImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var repository: PokedexRepository

    @RelaxedMockK
    private lateinit var useCase: GetPokemonListUseCase

    private var call: MockKStubScope<CallResult<List<Pokemon>>, CallResult<List<Pokemon>>>? = null
    private lateinit var pokemonList: List<Pokemon>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Default)
        call = coEvery { repository.getPokemonList() }
        useCase = GetPokemonListUseCaseImpl(repository)
        pokemonList = listOf(
            Pokemon("charizard", "www.pokeapi.com/pokemon/charizard")
        )
    }

    @Test
    fun `given fetch pokemon list method is called when call is successful then return pokemon list`() =
        runTest {
            call!!.returns(CallResult.Success(pokemonList))
            val expected = listOf(
                Pokemon("charizard", "www.pokeapi.com/pokemon/charizard")
            )
            val result = useCase.getPokemonList()

            assertTrue(result is CallResult.Success)
            assertEquals(result.data, expected)
        }

    @Test
    fun `given fetch pokemon list method is called when server returns error then return exception state`() =
        runTest {
            val httpException = Response.error<List<Pokemon>>(
                500,
                "{\"error\":[\"server offline\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            call!!.returns(
                CallResult.ServerError(HttpException(httpException))
            )
            val result = useCase.getPokemonList()

            assertTrue(result is CallResult.ServerError)
        }

    @Test
    fun `given fetch pokemon list method is called when theres connection issues then return exception state`() =
        runTest {
            val ioException = IOException()
            call!!.returns(CallResult.SocketError(ioException))
            val result = useCase.getPokemonList()

            assertTrue(result is CallResult.SocketError)
        }
}
