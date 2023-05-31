package com.iamironz.cpoilotweather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityCountry: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
    ): WeatherResponse
}
