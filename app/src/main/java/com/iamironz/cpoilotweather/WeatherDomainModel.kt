package com.iamironz.cpoilotweather

data class WeatherDomainModel(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain?,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int,
        val seaLevel: Int,
        val grndLevel: Int
    )

    data class Wind(
        val speed: Double,
        val deg: Int,
        val gust: Double
    )

    data class Rain(
        val h: Double?
    )

    data class Clouds(
        val all: Int
    )

    data class Sys(
        val type: Int,
        val id: Int,
        val country: String,
        val sunrise: Int,
        val sunset: Int
    )
}

class WeatherResponseToDomainMapper  {

    fun map(response: WeatherResponse) = WeatherDomainModel(
        coord = WeatherDomainModel.Coord(
            lon = response.coord.lon,
            lat = response.coord.lat
        ),
        weather = response.weather.map {
            WeatherDomainModel.Weather(
                id = it.id,
                main = it.main,
                description = it.description,
                icon = it.icon
            )
        },
        base = response.base,
        main = WeatherDomainModel.Main(
            temp = response.main.temp,
            feelsLike = response.main.feelsLike,
            tempMin = response.main.tempMin,
            tempMax = response.main.tempMax,
            pressure = response.main.pressure,
            humidity = response.main.humidity,
            seaLevel = response.main.seaLevel,
            grndLevel = response.main.grndLevel
        ),
        visibility = response.visibility,
        wind = WeatherDomainModel.Wind(
            speed = response.wind.speed,
            deg = response.wind.deg,
            gust = response.wind.gust
        ),
        rain = response.rain?.let {
            WeatherDomainModel.Rain(
                h = it.h
            )
        },
        clouds = WeatherDomainModel.Clouds(
            all = response.clouds.all
        ),
        dt = response.dt,
        sys = WeatherDomainModel.Sys(
            type = response.sys.type,
            id = response.sys.id,
            country = response.sys.country,
            sunrise = response.sys.sunrise,
            sunset = response.sys.sunset
        ),
        timezone = response.timezone,
        id = response.id,
        name = response.name,
        cod = response.cod
    )
}
