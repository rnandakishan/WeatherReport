package com.assignment.weatherreport.service

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface which creates proper query parameters
 *
 */
interface Webservice {
    /**
     * Get method to get the weather data which accepts current location string as well as API KEY
     *
     * @param location
     * @param appID
     * @return
     */
    @GET("weather")
    fun getCurrentWeatherData(@Query("q")location: String, @Query("appid")appID: String): Call<JsonObject>

    /**
     * Get method to get image from server with string as parameter
     *
     * @param iconStr
     * @return
     */
    @GET("{iconStr}@2x.png")
    fun getCurrentWeatherImage(@Path("iconStr")iconStr: String): Call<ResponseBody>
}