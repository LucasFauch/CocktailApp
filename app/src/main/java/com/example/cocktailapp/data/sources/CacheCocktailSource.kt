package com.example.cocktailapp.data.sources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailapp.CocktailAppApplication
import com.example.cocktailapp.data.AppDatabase
import com.example.cocktailapp.data.models.CocktailModel
import com.example.cocktailapp.data.repositories.CocktailSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.CountDownLatch

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails")
    fun getAll(): List<CocktailModel>

    @Query("SELECT * FROM cocktails WHERE name = :cocktailName")
    fun getByName(cocktailName: String): List<CocktailModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktail: CocktailModel?)
}

object CacheCocktailSource: CocktailSource{

    private lateinit var appDatabase: AppDatabase

    init{
        val appContext = CocktailAppApplication.getContext()
        val utilitiesEntryPoint = appContext?.let {
            EntryPointAccessors.fromApplication(it, DefaultCacheCocktailSourceEntryPoint::class.java)
        }
        appDatabase = utilitiesEntryPoint?.appDatabase!!
    }

    override suspend fun getCocktails(): List<CocktailModel> {

        OnlineCocktailSource.getCocktails().map{
            appDatabase.cocktailDao().insert(it)
        }

        return appDatabase.cocktailDao().getAll()
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultCacheCocktailSourceEntryPoint{
    var appDatabase: AppDatabase
}