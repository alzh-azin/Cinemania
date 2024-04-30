package com.example.cinemania.feature

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object Detail : Routes("details")
}