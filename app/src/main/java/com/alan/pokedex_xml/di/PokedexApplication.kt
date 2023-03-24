package com.alan.pokedex_xml.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokedexApplication : Application() {

    override fun onCreate() {
        startKoin {
            this.androidContext(this@PokedexApplication)
            this.modules(retrofitModule, pokedexModule)
        }

        super.onCreate()
    }
}
