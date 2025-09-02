package com.example.taller1.formula1

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taller1.navigation.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL
import java.net.URLEncoder

fun loadDrivers(): MutableList<Driver> {
    val driversArray = JSONArray(URL("https://api.openf1.org/v1/drivers?session_key=9684").readText())
    val drivers = mutableListOf<Driver>()

    for (i in 0 until driversArray.length()) {
        val jsonObject = driversArray.getJSONObject(i)
        val driver = Driver(
            meetingKey = jsonObject.getInt("meeting_key"),
            sessionKey = jsonObject.getInt("session_key"),
            driverNumber = jsonObject.getInt("driver_number"),
            broadcastName = jsonObject.getString("broadcast_name"),
            fullName = jsonObject.getString("full_name"),
            nameAcronym = jsonObject.getString("name_acronym"),
            teamName = jsonObject.getString("team_name"),
            teamColor = jsonObject.getString("team_colour"),
            firstName = jsonObject.getString("first_name"),
            lastName = jsonObject.getString("last_name"),
            headshotUrl = jsonObject.getString("headshot_url"),
            countryCode = if (jsonObject.isNull("country_code")) null else jsonObject.getString("country_code")
        )
        drivers.add(driver)
    }
    return drivers
}

@Composable
fun Formula1Screen(navController: NavController) {
    var drivers by remember { mutableStateOf(emptyList<Driver>()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val driverList = loadDrivers()
            CoroutineScope(Dispatchers.Main).launch {
                drivers = driverList
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            F1DriversList(drivers = drivers, navController = navController)
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
                        val encodedTeamName = URLEncoder.encode(driver.teamName, "UTF-8")
                        val encodedNameAcronym = URLEncoder.encode(driver.nameAcronym, "UTF-8")
                        val encodedFullName = URLEncoder.encode(driver.fullName, "UTF-8")
                        val encodedCountryCode = URLEncoder.encode(driver.countryCode ?: "", "UTF-8")
                        val encodedHeadshotUrl = URLEncoder.encode(driver.headshotUrl, "UTF-8")
                        val encodedTeamColor = URLEncoder.encode(driver.teamColor, "UTF-8")

                        navController.navigate(
                            "${AppScreens.F1Detail.name}/" +
                                    "$encodedTeamName/" +
                                    "$encodedNameAcronym/" +
                                    "$encodedFullName/" +
                                    "$encodedCountryCode/" +
                                    "$encodedHeadshotUrl/" +
                                    "$encodedTeamColor"
                        )
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
                            "${driver.driverNumber}. ${driver.fullName}",
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