package com.example.cinemania.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.feature.Routes

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            HomeRoute()
        }
    }
}