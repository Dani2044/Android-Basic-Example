package com.example.taller1.formula1

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taller1.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

private const val OPENF1_SESSION_KEY = 9158

fun loadDrivers(sessionKey: Int = OPENF1_SESSION_KEY): List<Driver> {
    val json = URL("https://api.openf1.org/v1/drivers?session_key=$sessionKey").readText()
    val arr = JSONArray(json)
    val out = mutableListOf<Driver>()
    for (i in 0 until arr.length()) {
        val jo = arr.getJSONObject(i)
        out += Driver(
            meetingKey = jo.optInt("meeting_key"),
            sessionKey = jo.optInt("session_key"),
            driverNumber = jo.optInt("driver_number"),
            broadcastName = jo.optString("broadcast_name", ""),
            fullName = jo.optString("full_name", ""),
            nameAcronym = jo.optString("name_acronym", ""),
            teamName = jo.optString("team_name", ""),
            teamColor = jo.optString("team_colour", ""),
            firstName = jo.optString("first_name", ""),
            lastName = jo.optString("last_name", ""),
            headshotUrl = jo.optString("headshot_url", ""),
            countryCode = jo.optString("country_code", "")
        )
    }
    return out.sortedBy { it.driverNumber }
}

@Composable
fun Formula1Screen(navController: NavController) {
    var drivers by remember { mutableStateOf<List<Driver>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        runCatching { withContext(Dispatchers.IO) { loadDrivers() } }
            .onSuccess { drivers = it }
            .onFailure {
                error = it.message
                drivers = emptyList()
                android.util.Log.e("F1", "Error loading drivers", it)
            }
    }

    Scaffold { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                error != null -> Text("Error: $error")
                drivers.isEmpty() -> Text("Loading…")
                else -> F1DriversList(drivers, navController)
            }
        }
    }
}

@Composable
fun F1DriversList(drivers: List<Driver>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(drivers) { driver ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val route =
                            AppScreens.F1Detail.name +
                                    "?teamName=${Uri.encode(driver.teamName)}" +
                                    "&nameAcronym=${Uri.encode(driver.nameAcronym)}" +
                                    "&fullName=${Uri.encode(driver.fullName)}" +
                                    "&countryCode=${Uri.encode(driver.countryCode ?: "")}" +
                                    "&headshotUrl=${Uri.encode(driver.headshotUrl)}" +
                                    "&teamColor=${Uri.encode(driver.teamColor)}"
                        navController.navigate(route)
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "${driver.driverNumber}. ${driver.fullName}",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Formula1ScreenPreview() {
    val navController = rememberNavController()
    Formula1Screen(navController)
}