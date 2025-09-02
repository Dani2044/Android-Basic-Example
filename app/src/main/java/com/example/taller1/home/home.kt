package com.example.taller1.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taller1.R
import com.example.taller1.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = colorResource(R.color.background))
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Select an option",
            color = colorResource(R.color.primary),
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Image(
            painter = painterResource(R.drawable.tictactoe),
            contentDescription = "TicTacToe",
            modifier = Modifier
                .size(250.dp)
                .clickable { navController.navigate(AppScreens.TicTacToe.name) }
        )

        Image(
            painter = painterResource(R.drawable.formula1),
            contentDescription = "F1 Drivers",
            modifier = Modifier
                .size(250.dp)
                .clickable { navController.navigate(AppScreens.Formula1.name) }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}