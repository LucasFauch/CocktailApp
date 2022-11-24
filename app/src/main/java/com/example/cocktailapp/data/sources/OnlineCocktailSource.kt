package com.example.cocktailapp.data.sources

import com.example.cocktailapp.data.models.CocktailModel
import com.example.cocktailapp.data.repositories.CocktailSource
import com.squareup.moshi.Json
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

object OnlineCocktailSource: CocktailSource {
    private const val BASE_URL = "www.thecocktaildb.com/api/json/v1/1"
    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    data class CocktailList(
        @Json(name="drinks")
        val list: List<OnlineCocktailModel>
    )

    data class OnlineCocktailModel(
        @Json(name="strDrink")
        val name: String,

        @Json(name="idDrink")
        val id: String,

        @Json(name="strDrinkThumb")
        val thumb: String
    )

    interface CocktailService{
        @GET("filter.php?c=Cocktail")
        fun getCocktails(): CocktailList
    }

    private val retrofitCocktailService: CocktailService by lazy {
        retrofit.create(CocktailService::class.java)
    }

    override suspend fun getCocktails(): List<CocktailModel> {
        println(retrofitCocktailService.getCocktails().list.map{
            CocktailModel(name = it.name, id = it.id)
        })
        println("WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
        return retrofitCocktailService.getCocktails().list.map{
            CocktailModel(name = it.name, id = it.id)
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
object CocktailSourceModule{
    @Provides
    @Singleton
    fun provideCocktailSource(): CocktailSource{
        return OnlineCocktailSource
    }
}