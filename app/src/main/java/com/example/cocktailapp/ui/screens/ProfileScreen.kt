package com.example.cocktailapp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cocktailapp.data.repositories.DefaultCocktailRepository
import com.example.cocktailapp.data.sources.OnlineCocktailSource
import com.example.cocktailapp.ui.viewmodels.CocktailViewModel

@Composable
fun Profile(navController: NavController, onCanNavigateBack: (Boolean) -> Unit){
    val viewModel: CocktailViewModel = viewModel()
    onCanNavigateBack(false)
    Text("Profile")
}