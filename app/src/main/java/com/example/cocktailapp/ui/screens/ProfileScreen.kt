package com.example.cocktailapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cocktailapp.data.states.CocktailUiState
import com.example.cocktailapp.ui.viewmodels.CocktailViewModel

@Composable
fun Profile(navController: NavController, onCanNavigateBack: (Boolean) -> Unit){
    val viewModel: CocktailViewModel = viewModel(factory = CocktailViewModel.Factory)
    val cocktailList = viewModel.uiState.collectAsState()

    onCanNavigateBack(false)
    ListCocktails(cocktailList = cocktailList.value)
}

@Composable
fun ListCocktails(cocktailList: List<CocktailUiState>, modifier: Modifier = Modifier){
    LazyColumn{
        items(cocktailList){ cocktail ->
            CocktailCard(cocktail = cocktail)
        }
    }
}

@Composable
fun CocktailCard(cocktail: CocktailUiState, modifier: Modifier = Modifier){
    Card(modifier = modifier.padding(8.dp), elevation = 4.dp) {
        Column{
            Text(
                text = cocktail.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}