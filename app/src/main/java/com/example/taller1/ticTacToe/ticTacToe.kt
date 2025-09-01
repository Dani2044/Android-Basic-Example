package com.example.taller1.ticTacToe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

@Composable
fun TicTacToeScreen(navController: NavController) {
    var player by remember {mutableStateOf(true)}
    var board by remember { mutableStateOf(List(9) { "" }) }
    Column (
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = colorResource(R.color.background))
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                "Turn of player:",
                color = colorResource(R.color.primary),
                fontSize = 30.sp
            )

            Image(
                painter = painterResource(
                    if (player) R.drawable.ex else R.drawable.circle
                ),
                contentDescription = "Player turn",
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 8.dp)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.board),
                contentDescription = "Board",
                modifier = Modifier.size(300.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.size(300.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        for (col in 0..2) {
                            val index = row * 3 + col
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(color = colorResource(R.color.background))
                                    .clickable(enabled = board[index].isEmpty()) {
                                        if (board[index].isEmpty()) {
                                            board = board.toMutableList().also {
                                                it[index] = if (player) "X" else "O"
                                            }
                                            player = !player
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                when (board[index]) {
                                    "X" -> Image(
                                        painter = painterResource(R.drawable.ex),
                                        contentDescription = "X",
                                        modifier = Modifier.size(64.dp)
                                    )
                                    "O" -> Image(
                                        painter = painterResource(R.drawable.circle),
                                        contentDescription = "O",
                                        modifier = Modifier.size(64.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TicTacToeScreenPreview() {
    val navController = rememberNavController()
    TicTacToeScreen(navController)
}