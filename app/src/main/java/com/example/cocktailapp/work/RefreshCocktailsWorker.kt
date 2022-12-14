package com.example.cocktailapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.data.sources.CacheCocktailSource

class RefreshCocktailsWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        try {
            CacheCocktailSource.refreshCocktails()
        }catch (e: Exception){
            return Result.retry()
        }
        return Result.success()
    }
}