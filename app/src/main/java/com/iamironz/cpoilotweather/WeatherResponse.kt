package com.iamironz.cpoilotweather

import com.google.gson.annotations.SerializedName

/*
* Create the data classes hierarchy (all data fields should be @SerializedName) by using the following json response:
*
{
  "coord": {
    "lon": 10.99,
    "lat": 44.34
  },
  "weather": [
    {
      "id": 501,
      "main": "Rain",
      "description": "moderate rain",
      "icon": "10d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 298.48,
    "feels_like": 298.74,
    "temp_min": 297.56,
    "temp_max": 300.05,
    "pressure": 1015,
    "humidity": 64,
    "sea_level": 1015,
    "grnd_level": 933
  },
  "visibility": 10000,
  "wind": {
    "speed": 0.62,
    "deg": 349,
    "gust": 1.18
  },
  "rain": {
    "1h": 3.16
  },
  "clouds": {
    "all": 100
  },
  "dt": 1661870592,
  "sys": {
    "type": 2,
    "id": 2075663,
    "country": "IT",
    "sunrise": 1661834187,
    "sunset": 1661882248
  },
  "timezone": 7200,
  "id": 3163858,
  "name": "Zocca",
  "cod": 200
}
*
* */

data class WeatherResponse(
    @SerializedName("coord") val coord: Coord,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: Main,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("dt") val dt: Int,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cod") val cod: Int
) {

    data class Coord(
        @SerializedName("lon") val lon: Double,
        @SerializedName("lat") val lat: Double
    )

    data class Weather(
        @SerializedName("id") val id: Int,
        @SerializedName("main") val main: String,
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
    )

    data class Main(
        @SerializedName("temp") val temp: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("temp_min") val tempMin: Double,
        @SerializedName("temp_max") val tempMax: Double,
        @SerializedName("pressure") val pressure: Int,
        @SerializedName("humidity") val humidity: Int,
        @SerializedName("sea_level") val seaLevel: Int,
        @SerializedName("grnd_level") val grndLevel: Int
    )

    data class Wind(
        @SerializedName("speed") val speed: Double,
        @SerializedName("deg") val deg: Int,
        @SerializedName("gust") val gust: Double
    )

    data class Rain(
        @SerializedName("1h") val h: Double
    )

    data class Clouds(
        @SerializedName("all") val all: Int
    )

    data class Sys(
        @SerializedName("type") val type: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("country") val country: String,
        @SerializedName("sunrise") val sunrise: Int,
        @SerializedName("sunset") val sunset: Int
    )
}
