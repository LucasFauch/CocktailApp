package com.example.cocktailapp.ui.screens

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.compose.AsyncImage
import com.example.cocktailapp.R
import com.example.cocktailapp.data.states.CocktailUiState
import com.example.cocktailapp.ui.viewmodels.CocktailViewModel

enum class AddProfileRoutes(@StringRes val route: Int){
    ProfileHome(R.string.profileHome),
    ProfileShare(R.string.profile)
}

fun NavGraphBuilder.profileNavigation(navController: NavController,
                                      modifier: Modifier = Modifier,
                                      onCanNavigateBack: (Boolean) -> Unit) {


    navigation(
        startDestination = AddProfileRoutes.ProfileHome.name,
        route = Screen.Profile.route
    ) {
        composable(route = AddProfileRoutes.ProfileHome.name) {
            onCanNavigateBack(false)
            profileHome(modifier, navController)
        }
        composable(route = AddProfileRoutes.ProfileShare.name) {
            onCanNavigateBack(true)
            profileShare(modifier, navController)
        }
    }
}

@Composable
fun profileHome(modifier: Modifier = Modifier, navController: NavController){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate(AddProfileRoutes.ProfileShare.name)}) {
            Text("Go to profil share")
        }
    }
}


@Composable
fun profileShare(modifier: Modifier = Modifier, navController: NavController){
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply(){
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Share your profil ?")
                putExtra(Intent.EXTRA_TEXT, "Hey, here is my profil")
            }
            context.startActivity(
                Intent.createChooser(intent, "Share your profil ?")
            )
        }) {
            Text(text = "Click to share your profil !")
        }
    }
}