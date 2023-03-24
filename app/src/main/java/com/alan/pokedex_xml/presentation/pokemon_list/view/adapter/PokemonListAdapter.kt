package com.alan.pokedex_xml.presentation.pokemon_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alan.pokedex_xml.common.ext.capitalizeFirst
import com.alan.pokedex_xml.databinding.ItemPokemonListBinding
import com.alan.pokedex_xml.domain.model.Pokemon

class PokemonListAdapter(
    val onClick: () -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private val pokemonList = mutableListOf<Pokemon>()
    private var diffUtil: DiffUtil.DiffResult? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    fun addList(newPokemonList: List<Pokemon>) {
        diffUtil = DiffUtil.calculateDiff(PokemonListDiffUtils(pokemonList, newPokemonList))
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        diffUtil?.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemPokemonListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            with(binding) {
                pokemonListPokemonItemNameTv.text = pokemon.name.capitalizeFirst()

                pokemonListPokemonItemMcv.setOnClickListener {
                    onClick()
                }
            }
        }
    }
}
