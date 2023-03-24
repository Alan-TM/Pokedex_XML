package com.alan.pokedex_xml.presentation.pokemon_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.domain.model.Pokemon
import com.alan.pokedex_xml.domain.use_case.pokemon_list.GetPokemonListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val useCase: GetPokemonListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    private val _errorState = MutableLiveData<CallResult<List<Pokemon>>>()
    val errorState: LiveData<CallResult<List<Pokemon>>> get() = _errorState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPokemonList() {
        viewModelScope.launch(dispatcher) {
            _isLoading.postValue(true)
            when (val result = useCase.getPokemonList()) {
                is CallResult.Success -> {
                    _pokemonList.postValue(result.data ?: emptyList())
                }
                else -> {
                    _errorState.postValue(result)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun setConnectionError() {
        _isLoading.value = false
        _errorState.value = CallResult.SocketError()
    }
}
