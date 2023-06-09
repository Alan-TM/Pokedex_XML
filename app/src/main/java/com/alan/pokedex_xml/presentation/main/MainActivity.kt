package com.alan.pokedex_xml.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alan.pokedex_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
