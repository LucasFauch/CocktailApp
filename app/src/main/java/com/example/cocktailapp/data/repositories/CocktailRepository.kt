package com.example.cocktailapp.data.repositories

import com.example.cocktailapp.data.models.CocktailModel

interface CocktailSource{
    suspend fun getCocktails(): List<CocktailModel>
}

interface CocktailRepository {
    suspend fun getCocktails(): List<CocktailModel>
    suspend fun getCocktailsFromCache(): List<CocktailModel>
}

class DefaultCocktailRepository(val cocktailSource: CocktailSource): CocktailRepository{
    override suspend fun getCocktails(): List<CocktailModel> {
        return cocktailSource.getCocktails()
    }

    override suspend fun getCocktailsFromCache(): List<CocktailModel> {
        return cocktailSource.getCocktails()
    }

}

class CachedCocktailRepository(val cocktailSource: CocktailSource, val cocktailCachingSource: CocktailSource): CocktailRepository{
    override suspend fun getCocktails(): List<CocktailModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCocktailsFromCache(): List<CocktailModel> {
        TODO("Not yet implemented")
    }

}