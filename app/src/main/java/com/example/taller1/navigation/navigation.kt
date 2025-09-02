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
            route =
                AppScreens.F1Detail.name +
                        "?teamName={teamName}" +
                        "&nameAcronym={nameAcronym}" +
                        "&fullName={fullName}" +
                        "&countryCode={countryCode}" +
                        "&headshotUrl={headshotUrl}" +
                        "&teamColor={teamColor}",
            arguments = listOf(
                navArgument("teamName") { type = NavType.StringType; nullable = true; defaultValue = "" },
                navArgument("nameAcronym") { type = NavType.StringType; nullable = true; defaultValue = "" },
                navArgument("fullName") { type = NavType.StringType; nullable = true; defaultValue = "" },
                navArgument("countryCode") { type = NavType.StringType; nullable = true; defaultValue = "" },
                navArgument("headshotUrl") { type = NavType.StringType; nullable = true; defaultValue = "" },
                navArgument("teamColor") { type = NavType.StringType; nullable = true; defaultValue = "" },
            )
        ) { backStackEntry ->
            F1DetailScreen(
                teamName = backStackEntry.arguments?.getString("teamName") ?: "",
                nameAcronym = backStackEntry.arguments?.getString("nameAcronym") ?: "",
                fullName = backStackEntry.arguments?.getString("fullName") ?: "",
                countryCode = backStackEntry.arguments?.getString("countryCode") ?: "",
                headshotUrl = backStackEntry.arguments?.getString("headshotUrl") ?: "",
                teamColor = backStackEntry.arguments?.getString("teamColor") ?: ""
            )
        }
    }
}