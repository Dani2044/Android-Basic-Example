package com.example.taller1.formula1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.net.URLDecoder

@Composable
fun F1DetailScreen(
    teamName: String,
    nameAcronym: String,
    fullName: String,
    countryCode: String,
    headshotUrl: String,
    teamColor: String
) {
    val decodedTeamName = URLDecoder.decode(teamName, "UTF-8")
    val decodedNameAcronym = URLDecoder.decode(nameAcronym, "UTF-8")
    val decodedFullName = URLDecoder.decode(fullName, "UTF-8")
    val decodedCountryCode = URLDecoder.decode(countryCode, "UTF-8")
    val decodedHeadshotUrl = URLDecoder.decode(headshotUrl, "UTF-8")
    val decodedTeamColor = URLDecoder.decode(teamColor, "UTF-8")

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Team: $decodedTeamName",
                fontSize = 24.sp
            )
            Text(
                text = "Name Acronym: $decodedNameAcronym",
                fontSize = 20.sp
            )
            Text(
                text = "Full Name: $decodedFullName",
                fontSize = 20.sp
            )
            Text(
                text = "Country: ${if (decodedCountryCode.isEmpty()) "N/A" else decodedCountryCode}",
                fontSize = 16.sp
            )
            Text(
                text = "Headshot URL: [Image]",
                fontSize = 16.sp
            )
            Text(
                text = "Team Color: $decodedTeamColor",
                fontSize = 16.sp
            )
        }
    }
}