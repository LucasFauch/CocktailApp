package com.example.cocktailapp.data.repositories

import com.example.cocktailapp.data.models.CocktailModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

interface CocktailSource{
    suspend fun getCocktails(): List<CocktailModel>
}

interface CocktailRepository {
    suspend fun getCocktails(): List<CocktailModel>
}

class DefaultCocktailRepository(): CocktailRepository{
    @Inject
    lateinit var cocktailSource: CocktailSource

    override suspend fun getCocktails(): List<CocktailModel> {
        return cocktailSource.getCocktails()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object CocktailRepositoryModule{
    @Provides
    fun provideCocktailRepository(): CocktailRepository{
        return DefaultCocktailRepository()
    }
}