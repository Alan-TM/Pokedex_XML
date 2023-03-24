package com.alan.pokedex_xml.common.ext

import com.alan.pokedex_xml.common.Constants.EMPTY_STRING

fun String.capitalizeFirst(): String {
    if (this.isEmpty()) {
        return EMPTY_STRING
    }

    return this.replaceFirst(this[0], this[0].uppercaseChar())
}
