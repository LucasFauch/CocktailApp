package com.example.cocktailapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun CocktailsComposition(modifier: Modifier = Modifier, navController: NavController){
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply(){
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Check the composition of the cocktail ?")
                putExtra(Intent.EXTRA_TEXT, "Check the composition of the cocktail")
            }
            context.startActivity(
                Intent.createChooser(intent, "Share the cocktail ?")
            )
        }) {
            Text(text = "Share the cocktail composition")
        }
    }
}