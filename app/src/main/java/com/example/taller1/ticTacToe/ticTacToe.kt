package com.example.taller1.ticTacToe

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1.R

@Composable
fun TicTacToeScreen() {
    var player by remember { mutableStateOf(true) }
    var board by remember { mutableStateOf(List(9) { "" }) }
    var winner by remember { mutableStateOf("") }
    var winningLine by remember { mutableStateOf(listOf<Int>()) }
    val context = LocalContext.current

    fun checkWinner(): Boolean {
        val lines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        for (line in lines) {
            val (a, b, c) = line
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                winner = board[a]
                winningLine = line
                Toast.makeText(context, "$winner wins!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    val primary = colorResource(R.color.primary)
    val primaryVariant = colorResource(R.color.primary_variant)
    val bg = colorResource(R.color.background)
    val winGreen = colorResource(R.color.win)
    val secondary = colorResource(R.color.secondary)
    val new = colorResource(R.color.new_button)

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = bg)
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "PLAYER ONE (X)",
            color = if (player) primary else secondary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(320.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.board),
                contentDescription = "Board",
                modifier = Modifier.size(320.dp)
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
                            val value = board[index]
                            val baseCellColor =
                                if (winner.isNotEmpty() && index in winningLine) winGreen
                                else primaryVariant

                            Button(
                                onClick = {
                                    if (board[index].isEmpty() && winner.isEmpty()) {
                                        board = board.toMutableList().also {
                                            it[index] = if (player) "X" else "O"
                                        }
                                        if (!checkWinner()) player = !player
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .height(84.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = baseCellColor,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                            ) {
                                val cellText = when (value) {
                                    "X" -> "X"
                                    "O" -> "O"
                                    else -> ""
                                }
                                Text(
                                    text = cellText,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PLAYER TWO (O)",
                color = if (!player) primary else secondary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    board = List(9) { "" }
                    player = true
                    winner = ""
                    winningLine = emptyList()
                },
                modifier = Modifier
                    .height(56.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = bg,
                    contentColor = primary
                )
            ) {
                Text("New Game", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview
@Composable
fun TicTacToeScreenPreview() {
    TicTacToeScreen()
}