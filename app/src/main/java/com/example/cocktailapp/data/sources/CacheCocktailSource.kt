package com.example.cocktailapp.data.sources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cocktailapp.CocktailAppApplication
import com.example.cocktailapp.data.AppDatabase
import com.example.cocktailapp.data.models.CocktailModel
import com.example.cocktailapp.data.repositories.CocktailSource
import com.example.cocktailapp.work.RefreshCocktailsWorker
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails")
    fun getAll(): Flow<List<CocktailModel>>

    @Query("SELECT * FROM cocktails WHERE name = :cocktailName")
    fun getByName(cocktailName: String): Flow<List<CocktailModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktails: List<CocktailModel>)
}

object CacheCocktailSource: CocktailSource{

    private var appDatabase: AppDatabase

    init{
        val appContext = CocktailAppApplication.getContext()
        val utilitiesEntryPoint = appContext?.let {
            EntryPointAccessors.fromApplication(it, DefaultCacheCocktailSourceEntryPoint::class.java)
        }
        appDatabase = utilitiesEntryPoint?.appDatabase!!

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshCocktailsWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(CocktailAppApplication.getContext()!!).enqueue(repeatingRequest)
    }

    suspend fun refreshCocktails(){
        withContext(Dispatchers.IO){
            val cocktails = OnlineCocktailSource.getCocktails()
            appDatabase.cocktailDao().insert(cocktails)
        }
    }

    override suspend fun getCocktails(): Flow<List<CocktailModel>> {
        //refreshCocktails()
        return appDatabase.cocktailDao().getAll()
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DefaultCacheCocktailSourceEntryPoint{
    var appDatabase: AppDatabase
}