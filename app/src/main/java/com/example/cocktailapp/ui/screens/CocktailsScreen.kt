package com.example.cocktailapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.cocktailapp.R

enum class AddCocktailsRoutes(@StringRes val route: Int){
    CocktailsList(R.string.cocktails_list),
    CocktailsComposition(R.string.cocktails_composition)
}

fun NavGraphBuilder.cocktailsNavigation(navController: NavController,
                                        modifier: Modifier = Modifier,
                                        onCanNavigateBack: (Boolean) -> Unit) {


    navigation(
        startDestination = AddCocktailsRoutes.CocktailsList.name,
        route = Screen.Cocktails.route
    ) {
        composable(route = AddCocktailsRoutes.CocktailsList.name) {
            onCanNavigateBack(false)
            CocktailsList(modifier, navController)
        }
        composable(route = AddCocktailsRoutes.CocktailsComposition.name) {
            onCanNavigateBack(true)
            CocktailsComposition(modifier, navController)
        }
    }
}

/*
@Composable
fun Cocktails(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            modifier = Modifier.clickable {
                navController.navigate(route = AddCocktailsRoutes.CocktailsComposition.name)
            },
            text = "Liste Cocktails",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = MaterialTheme.typography.h3.fontSize
        )
    }
}*/

/*Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
){
    Text(
        modifier = Modifier.clickable {
            navController2.navigate(route = InsideCocktailsScreens.CocktailsComposition.route)
        },
        text = "Composition cocktails",
        color = MaterialTheme.colors.primaryVariant,
        fontSize = MaterialTheme.typography.h3.fontSize
    )
}*/