package com.example.cocktailapp.data.repositories

import com.example.cocktailapp.CocktailAppApplication
import com.example.cocktailapp.data.models.CocktailModel
import com.example.cocktailapp.data.sources.OnlineCocktailSource
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface CocktailSource{
    suspend fun getCocktails(): List<CocktailModel>
}

interface CocktailRepository {
    suspend fun getCocktailsList(): List<CocktailModel>
}

class DefaultCocktailRepository @Inject constructor(): CocktailRepository{
    private lateinit var cocktailSource: CocktailSource

    init{
        val appContext = CocktailAppApplication.getContext()
        val utilitiesEntryPoint = appContext?.let{
            EntryPointAccessors.fromApplication(it, DefaultCocktailRepositoryEntryPoint::class.java)
        }
        cocktailSource = utilitiesEntryPoint?.cocktailSource!!
    }

    override suspend fun getCocktailsList(): List<CocktailModel> {
        return cocktailSource.getCocktails()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultCocktailRepositoryEntryPoint{
    var cocktailSource: CocktailSource
}

@InstallIn(SingletonComponent::class)
@Module
object CocktailRepositoryModule{
    @Singleton
    @Provides
    fun provideCocktailRepository(): CocktailRepository{
        return DefaultCocktailRepository()
    }
}


@InstallIn(SingletonComponent::class)
@Module
object CocktailSourceModule {
    @Provides
    @Singleton
    fun provideCocktailSource(): CocktailSource {
        return OnlineCocktailSource
    }
}