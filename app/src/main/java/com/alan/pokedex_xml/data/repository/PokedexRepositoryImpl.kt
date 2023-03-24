package com.alan.pokedex_xml.data.repository

import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.data.mapper.DataMapper.toPokemonList
import com.alan.pokedex_xml.data.remote.PokedexService
import com.alan.pokedex_xml.domain.model.Pokemon
import com.alan.pokedex_xml.domain.repository.PokedexRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PokedexRepositoryImpl(
    private val service: PokedexService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokedexRepository {

    override suspend fun getPokemonList(): CallResult<List<Pokemon>> = withContext(dispatcher) {
        return@withContext try {
            val response = service.getPokemonList()
            CallResult.Success(data = response.body()?.toPokemonList() ?: emptyList())
        } catch (e: HttpException) {
            CallResult.ServerError(e, e.code())
        } catch (e: IOException) {
            CallResult.SocketError(e)
        }
    }
}
