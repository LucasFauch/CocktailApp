package com.example.cocktailapp

import android.app.Application
import com.example.cocktailapp.data.AppContainer
import com.example.cocktailapp.data.DefaultAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CocktailAppApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}