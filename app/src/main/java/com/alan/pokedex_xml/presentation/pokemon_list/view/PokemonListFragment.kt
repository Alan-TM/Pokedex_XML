package com.alan.pokedex_xml.presentation.pokemon_list.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.alan.pokedex_xml.R
import com.alan.pokedex_xml.common.CallResult
import com.alan.pokedex_xml.common.UiErrorType
import com.alan.pokedex_xml.common.UiErrorType.SERVER_ERROR
import com.alan.pokedex_xml.common.UiErrorType.SOCKET_ERROR
import com.alan.pokedex_xml.common.UiErrorType.UNKNOWN_ERROR
import com.alan.pokedex_xml.common.ext.hasInternet
import com.alan.pokedex_xml.common.ext.hide
import com.alan.pokedex_xml.common.ext.show
import com.alan.pokedex_xml.databinding.FragmentPokemonListBinding
import com.alan.pokedex_xml.presentation.pokemon_list.view.adapter.PokemonListAdapter
import com.alan.pokedex_xml.presentation.pokemon_list.viewmodel.PokemonListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel: PokemonListViewModel by viewModel()
    private var pokemonListAdapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPokemonListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        if (hasInternet()) {
            viewModel.fetchPokemonList()
            initAdapter()
            initComponents(binding)
        } else {
            viewModel.setConnectionError()
        }
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentPokemonListBinding) {
        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            pokemonListAdapter?.addList(pokemonList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoadingComponents(isLoading, binding)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                is CallResult.ServerError -> showErrorComponents(binding, SERVER_ERROR)
                is CallResult.SocketError -> showErrorComponents(binding, SOCKET_ERROR)
                is CallResult.UnknownError -> {} // TODO add logic for unknown error
                else -> {}
            }
        }
    }

    private fun initAdapter() {
        pokemonListAdapter = PokemonListAdapter {
            // TODO add pokemon details fragment & navigation
            Toast.makeText(requireContext(), "Test", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initComponents(binding: FragmentPokemonListBinding) {
        with(binding) {
            pokemonListPokedexListRv.adapter = pokemonListAdapter
            pokemonListPokedexListRv.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showLoadingComponents(isLoading: Boolean, binding: FragmentPokemonListBinding) {
        if (isLoading) {
            binding.pokemonListPokedexListCel.root.hide()
            binding.pokemonListPokedexListRv.hide()
            binding.pokemonListSearchBoxTil.hide()
            binding.pokemonListPokedexListPb.show()
        } else {
            binding.pokemonListPokedexListCel.root.hide()
            binding.pokemonListPokedexListRv.show()
            binding.pokemonListSearchBoxTil.show()
            binding.pokemonListPokedexListPb.hide()
        }
    }

    private fun showErrorComponents(binding: FragmentPokemonListBinding, errorType: UiErrorType) {
        binding.pokemonListPokedexListCel.root.show()

        when (errorType) {
            SERVER_ERROR -> {
                binding.pokemonListPokedexListCel.componentErrorIcon.load(R.drawable.ic_server_error)
                binding.pokemonListPokedexListCel.componentErrorTitle.text =
                    getString(R.string.app_error_server_error_title)
                binding.pokemonListPokedexListCel.componentErrorMessage.text =
                    getString(R.string.app_error_server_error_message)
            }
            SOCKET_ERROR -> {
                binding.pokemonListPokedexListCel.componentErrorIcon.load(R.drawable.ic_socket_error)
                binding.pokemonListPokedexListCel.componentErrorTitle.text =
                    getString(R.string.app_error_socket_error_title)
                binding.pokemonListPokedexListCel.componentErrorMessage.text =
                    getString(R.string.app_error_socket_error_message)
            }
            UNKNOWN_ERROR -> {} // TODO add unknown error logic
        }
    }
}
