package com.example.cocktailapp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Profile(navController: NavController, onCanNavigateBack: (Boolean) -> Unit){
    onCanNavigateBack(false)
    Text("Profile")
}