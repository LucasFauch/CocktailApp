package com.example.cocktailapp.data

import com.example.cocktailapp.data.repositories.CocktailRepository
import com.example.cocktailapp.data.repositories.CocktailSource
import com.example.cocktailapp.data.repositories.DefaultCocktailRepository
import com.example.cocktailapp.data.sources.OnlineCocktailSource

interface AppContainer {
    val cocktailSource: CocktailSource
    val cocktailRepository: CocktailRepository
}

class DefaultAppContainer: AppContainer{
    override val cocktailSource: CocktailSource by lazy {
        OnlineCocktailSource
    }

    override val cocktailRepository: CocktailRepository by lazy {
        DefaultCocktailRepository()
    }
}