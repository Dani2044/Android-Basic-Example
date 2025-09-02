package com.example.taller1.formula1

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.core.graphics.toColorInt

@Composable
fun F1DetailScreen(
    teamName: String,
    nameAcronym: String,
    fullName: String,
    countryCode: String,
    headshotUrl: String,
    teamColor: String
) {
    val dTeam  = Uri.decode(teamName)
    val dAcr   = Uri.decode(nameAcronym)
    val dName  = Uri.decode(fullName)
    val dCty   = Uri.decode(countryCode)
    val dPhoto = Uri.decode(headshotUrl)
    val dColor = Uri.decode(teamColor)

    val teamColorParsed = remember(dColor) { parseTeamColor(dColor) }
    val alpha2 = remember(dCty) { alpha3ToAlpha2(dCty) }
    val flagUrl = alpha2?.let { "https://flagcdn.com/w320/${it.lowercase()}.png" }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier.width(140.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(if (dPhoto.isBlank()) FALLBACK_HEADSHOT else dPhoto)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Driver headshot",
                        modifier = Modifier
                            .size(width = 120.dp, height = 140.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(flagUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Country flag",
                        modifier = Modifier
                            .width(180.dp)
                            .height(110.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(92.dp),
                horizontalAlignment = Alignment.Start
            ) {
                InfoRow(label = "Name:", value = dName)
                InfoRow(label = "Team:", value = dTeam, valueColor = teamColorParsed, boldValue = true)
                InfoRow(label = "Acronym:", value = dAcr)
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified,
    boldValue: Boolean = false
) {
    val labelBlue = Color(0xFF2196F3)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = labelBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value.ifBlank { "N/A" },
            color = valueColor,
            fontSize = 24.sp,
            fontWeight = if (boldValue) FontWeight.Medium else FontWeight.Normal
        )
    }
}

private fun parseTeamColor(hex: String?): Color {
    val s = (hex ?: "").trim()
    if (s.isBlank()) return Color.Unspecified
    val normalized = if (s.startsWith("#")) s else "#$s"
    return try {
        Color(normalized.toColorInt())
    } catch (_: IllegalArgumentException) {
        Color.Unspecified
    }
}

private fun alpha3ToAlpha2(alpha3: String?): String? {
    val map = mapOf(
        "GBR" to "GB", "NED" to "NL", "NLD" to "NL",
        "ESP" to "ES", "FRA" to "FR", "DEU" to "DE", "GER" to "DE",
        "ITA" to "IT", "USA" to "US", "CAN" to "CA", "MEX" to "MX",
        "BRA" to "BR", "ARG" to "AR", "AUS" to "AU", "NZL" to "NZ",
        "PRT" to "PT", "BEL" to "BE", "CHE" to "CH", "SUI" to "CH",
        "AUT" to "AT", "DNK" to "DK", "SWE" to "SE", "FIN" to "FI",
        "NOR" to "NO", "IRL" to "IE", "HUN" to "HU", "CZE" to "CZ",
        "POL" to "PL", "ROU" to "RO", "JPN" to "JP", "CHN" to "CN",
        "KOR" to "KR", "SGP" to "SG", "THA" to "TH", "UAE" to "AE",
        "SAU" to "SA", "QAT" to "QA", "TUR" to "TR", "MON" to "MC"
    )
    return map[alpha3?.uppercase()?.take(3)]
}

private const val FALLBACK_HEADSHOT =
    "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/unknown-driver.png.transform/1col/image.png"

@Preview(showBackground = true)
@Composable
fun F1DetailScreenPreview() {
    F1DetailScreen(
        teamName = "McLaren",
        nameAcronym = "NOR",
        fullName = "Lando NORRIS",
        countryCode = "GBR",
        headshotUrl = "",
        teamColor = "F175D51C"
    )
}