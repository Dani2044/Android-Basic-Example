package com.example.taller1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taller1.formula1.F1DetailScreen
import com.example.taller1.formula1.Formula1Screen
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
        composable(
            route = "${AppScreens.F1Detail.name}/{teamName}/{nameAcronym}/{fullName}/{countryCode}/{headshotUrl}/{teamColor}",
            arguments = listOf(
                navArgument("teamName") { type = NavType.StringType },
                navArgument("nameAcronym") { type = NavType.StringType },
                navArgument("fullName") { type = NavType.StringType },
                navArgument("countryCode") { type = NavType.StringType },
                navArgument("headshotUrl") { type = NavType.StringType },
                navArgument("teamColor") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamName = backStackEntry.arguments?.getString("teamName") ?: ""
            val nameAcronym = backStackEntry.arguments?.getString("nameAcronym") ?: ""
            val fullName = backStackEntry.arguments?.getString("fullName") ?: ""
            val countryCode = backStackEntry.arguments?.getString("countryCode") ?: ""
            val headshotUrl = backStackEntry.arguments?.getString("headshotUrl") ?: ""
            val teamColor = backStackEntry.arguments?.getString("teamColor") ?: ""

            F1DetailScreen(
                teamName = teamName,
                nameAcronym = nameAcronym,
                fullName = fullName,
                countryCode = countryCode,
                headshotUrl = headshotUrl,
                teamColor = teamColor
            )
        }
    }
}