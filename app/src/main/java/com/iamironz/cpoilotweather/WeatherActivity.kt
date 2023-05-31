package com.iamironz.cpoilotweather

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.iamironz.cpoilotweather.ui.theme.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    @Inject
    lateinit var weatherApiService: WeatherApiService

    @Inject
    lateinit var cityCountryRegex: Regex

    @Inject
    lateinit var mapper: WeatherResponseToDomainMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(
                        lifecycleScope,
                        weatherApiService,
                        cityCountryRegex,
                    )
                }
            }
        }
    }

    /*
    implement WeatherScreen composable function, where you will use the apiService to get the weather data
    after the user clicks the button
    display it in the Text composable
    */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeatherScreen(
        scope: CoroutineScope,
        weatherApiService: WeatherApiService,
        cityCountryRegex: Regex
    ) {

        var textFieldState by remember { mutableStateOf(TextFieldValue("")) }
        var weatherData by remember { mutableStateOf<String?>(null) }
        var isValidInput by remember { mutableStateOf(true) }

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = {

                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = { newValue ->
                        textFieldState = newValue
                        isValidInput = cityCountryRegex.matches(newValue.text)
                    },
                    label = { Text("Enter city and country") },
                    isError = !isValidInput,
                )

                Spacer(Modifier.height(16.dp))

                // after the user clicks the button and display it in the Text composable
                Button(onClick = {
                    if (isValidInput) {
                        scope.launch {
                            weatherData = weatherResponse(
                                weatherApiService,
                                mapper,
                                textFieldState
                            )
                            hideKeyboard(this@WeatherActivity)
                        }
                    } else {
                        weatherData = "Invalid input"
                    }
                }) {
                    Text("Get Weather")
                }

                Spacer(Modifier.height(16.dp))

                //make text size 42sp and bold
                Text(
                    text = weatherData ?: "Enter comma separated city and country",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            },
        )
    }

    // implement hideKeyboard function
    private fun hideKeyboard(weatherActivity: WeatherActivity) {
        weatherActivity.currentFocus?.let { view ->
            val imm = weatherActivity.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private suspend fun weatherResponse(
        weatherApiService: WeatherApiService,
        mapper: WeatherResponseToDomainMapper,
        textFieldState: TextFieldValue
    ): String {
        val response = weatherApiService.getWeather(textFieldState.text, APIKEY)
        val weather = mapper.map(response)
        return "${response.main.temp}${CELSIUS}, \n" +
                "feels like: ${weather.main.feelsLike}${CELSIUS} \n" +
                "conditions: ${weather.weather[0].description}"
    }

    companion object {
        private const val CELSIUS = "°C"
        private const val APIKEY = "b0b32241ee64c7fc8c77129c490fbfb1"
    }
}
