package com.example.cocktailapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktailapp.CocktailAppApplication
import com.example.cocktailapp.data.models.CocktailModel
import com.example.cocktailapp.data.repositories.CocktailSource
import com.example.cocktailapp.data.sources.CacheCocktailSource
import com.example.cocktailapp.data.sources.CocktailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = arrayOf(CocktailModel::class), version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cocktailDao(): CocktailDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase?=null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "cocktailAppDatabase").build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
object AppDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(CocktailAppApplication.getContext()!!)
    }
}