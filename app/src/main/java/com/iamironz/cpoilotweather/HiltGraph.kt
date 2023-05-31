package com.iamironz.cpoilotweather

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltGraph {

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    //generate regex for "city name, country code" format
    @Singleton
    @Provides
    fun provideCityCountryRegex() = Regex("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*, [a-zA-Z]{2,3}\$")

    @Singleton
    @Provides
    fun provideWeatherResponseToDomainMapper() = WeatherResponseToDomainMapper()
}
