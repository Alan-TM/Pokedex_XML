package com.alan.pokedex_xml.common

import android.content.Context
import androidx.annotation.StringRes

sealed class StringHandler {
    data class DynamicString(val value: String) : StringHandler()
    data class StringResource(
        @StringRes val id: Int,
        val arguments: List<Any>? = null
    ) : StringHandler()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> arguments?.let {
                context.getString(id, *it.toTypedArray())
            } ?: context.getString(id)
        }
    }
}
