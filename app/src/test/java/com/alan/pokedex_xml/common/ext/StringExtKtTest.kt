package com.alan.pokedex_xml.common.ext

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StringExtKtTest {
    private lateinit var pokemoneOne: String
    private lateinit var pokemoneTwo: String

    @Before
    fun setUp() {
        pokemoneOne = "ditto"
        pokemoneTwo = ""
    }

    @Test
    fun `when pokemon name is not empty then return first letter uppercase`() {
        assertEquals(pokemoneOne.capitalizeFirst(), "Ditto")
    }

    @Test
    fun `when pokemon name is empty then return empty`() {
        assertEquals(pokemoneTwo.capitalizeFirst(), "")
    }
}
