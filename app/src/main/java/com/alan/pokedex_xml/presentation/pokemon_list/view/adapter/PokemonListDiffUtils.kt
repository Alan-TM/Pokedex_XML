package com.alan.pokedex_xml.presentation.pokemon_list.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alan.pokedex_xml.domain.model.Pokemon

class PokemonListDiffUtils(
    private val oldList: List<Pokemon>,
    private val newList: List<Pokemon>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        when {
            oldList[oldItemPosition].name == newList[newItemPosition].name -> true
            oldList[oldItemPosition].url == newList[newItemPosition].url -> true
            else -> false
        }
}
