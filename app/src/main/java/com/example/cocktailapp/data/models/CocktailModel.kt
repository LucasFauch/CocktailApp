package com.example.cocktailapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailModel(
    @PrimaryKey
    var id: Int,

    @NonNull @ColumnInfo(name="name")
    var name: String,

    @NonNull @ColumnInfo(name="cocktail_id")
    var cocktailId: Int
)