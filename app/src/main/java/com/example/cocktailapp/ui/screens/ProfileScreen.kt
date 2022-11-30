package com.example.cocktailapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cocktailapp.data.states.CocktailUiState
import com.example.cocktailapp.ui.viewmodels.CocktailViewModel

@Composable
fun Profile(modifier: Modifier = Modifier,
            viewModel: CocktailViewModel = hiltViewModel(),
            navController: NavController,
            onCanNavigateBack: (Boolean) -> Unit){
    val cocktailList = viewModel.uiState.collectAsState()
    onCanNavigateBack(false)

    ListCocktails(modifier = modifier, cocktailList = cocktailList.value)
}

@Composable
fun ListCocktails(cocktailList: List<CocktailUiState>, modifier: Modifier = Modifier){
    LazyColumn() {
        items(count = cocktailList.size){
            CocktailCard(cocktail = cocktailList[it])
        }
    }
}

@Composable
fun CocktailCard(cocktail: CocktailUiState, modifier: Modifier = Modifier){
    Card(modifier = modifier
        .fillMaxSize()
        .padding(8.dp),
        backgroundColor = Color.LightGray,
        elevation = 4.dp) {
        //padding(8.dp)
        Row(){
            AsyncImage(cocktail.thumb, contentDescription = null)
            Text(
                text = cocktail.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}