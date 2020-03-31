package com.assignment.weatherreport.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create 2 retrofit client objects with 2 different base urls
 */
object RetrofitClient {
    /**
     * Base url to get weather data
     */
    val webservice: Webservice by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(Webservice::class.java)
    }

    /**
     * Base url to get image
     */
    val webserviceImg: Webservice by lazy {
        Retrofit.Builder()
            .baseUrl("http://openweathermap.org/img/wn/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(Webservice::class.java)
    }
}