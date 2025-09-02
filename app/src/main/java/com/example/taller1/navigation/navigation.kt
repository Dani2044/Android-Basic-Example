package com.example.taller1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.taller1.formula1.Formula1Screen
import com.example.taller1.formula1.F1DetailScreen
import com.example.taller1.home.HomeScreen
import com.example.taller1.ticTacToe.TicTacToeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Home.name) {
        composable(route = AppScreens.Home.name) {
            HomeScreen(navController)
        }
        composable(route = AppScreens.TicTacToe.name) {
            TicTacToeScreen()
        }
        composable(route = AppScreens.Formula1.name) {
            Formula1Screen(navController)
        }

        composable(route = AppScreens.F1Detail.name) {
            F1DetailScreen()
        }
    }
}