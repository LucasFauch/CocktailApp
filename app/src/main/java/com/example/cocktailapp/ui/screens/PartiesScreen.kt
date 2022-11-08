package com.example.cocktailapp.ui.screens

import androidx.compose.material.Button

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

enum class AddPartiesRoutes(val route: String){
    PartiesList("Parties List"),
    PartiesComposition("Parties Composition")
}

fun NavGraphBuilder.partiesNavigation(navController: NavController,
                                        modifier: Modifier = Modifier,
                                        onCanNavigateBack: (Boolean) -> Unit) {


    navigation(
        startDestination = AddPartiesRoutes.PartiesList.name,
        route = Screen.Parties.route
    ) {
        composable(route = AddPartiesRoutes.PartiesList.name) {
            onCanNavigateBack(false)
            PartiesList(modifier, navController)
        }
        composable(route = AddPartiesRoutes.PartiesComposition.name) {
            onCanNavigateBack(true)
            PartiesComposition(modifier, navController)
        }
    }
}

@Composable
fun PartiesList(modifier: Modifier = Modifier, navController: NavController){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate(AddPartiesRoutes.PartiesComposition.name)}) {
            Text("To Parties Compositions")
        }
    }
}

@Composable
fun PartiesComposition(modifier: Modifier = Modifier, navController: NavController){
    Text("Parties composition")
}