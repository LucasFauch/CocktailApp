package com.example.cocktailapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.cocktailapp.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon : ImageVector) {
    object Cocktails : Screen("cocktails", R.string.cocktails, Icons.Filled.Search)
    object Parties : Screen("parties", R.string.parties, Icons.Filled.List)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
    object Friends : Screen("friends", R.string.friends, Icons.Default.Face)
}

@Composable
fun TopBar(@StringRes title: Int,
           canNavigateBack: Boolean,
           modifier: Modifier = Modifier,
           navigateBack: ()->Unit) {
    TopAppBar(
        title = { Text(stringResource(id = title)) },
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier, onNavigate: (Screen)->Unit) {
    val items = listOf(Screen.Cocktails,
        Screen.Parties,
        Screen.Profile,
        Screen.Friends)
    BottomNavigation(modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    onNavigate(screen)
                }
            )
        }
    }
}

@Composable
fun AppScreen(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentScreenTitle by remember {
        mutableStateOf(Screen.Parties.resourceId)
    }

    var canNavigateBack by remember {
        mutableStateOf(false)//navBackStackEntry?.destination?.hierarchy?.any() == false)
    }

    Scaffold(
        topBar = {
            TopBar(title = currentScreenTitle, canNavigateBack = canNavigateBack, modifier) {
                navController.navigateUp()
            }
        },
        bottomBar = {
            BottomBar(navController, modifier) {
                currentScreenTitle = it.resourceId
            }
        }) {//Pour changer la page d'accueil de l'appli
            innerPadding ->
        NavHost(navController, startDestination = Screen.Parties.route, Modifier.padding(innerPadding)) {
            cocktailsNavigation(navController, modifier, onCanNavigateBack = {
                canNavigateBack = it
            })
            //composable(Screen.Cocktails.route) { Cocktails(navController) }
            //composable(Screen.Parties.route) { Parties(navController) }
            partiesNavigation(navController, modifier, onCanNavigateBack = {
                canNavigateBack = it
            })
            composable(Screen.Friends.route) { Friends(navController, onCanNavigateBack = {
                canNavigateBack = it
            }) }
            partiesNavigation(navController, modifier, onCanNavigateBack = {
                canNavigateBack = it
            })
            /*composable(Screen.Profile.route) { Profile(modifier = modifier, navController = navController, onCanNavigateBack = {
                canNavigateBack = it
            })}*/
            profileNavigation(navController, modifier, onCanNavigateBack = {
                canNavigateBack = it
            })
        }
    }
}