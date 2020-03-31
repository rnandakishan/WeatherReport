package com.assignment.weatherreport.repo

import androidx.lifecycle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import android.graphics.BitmapFactory
import android.util.Log
import com.assignment.weatherreport.MyApplication
import com.assignment.weatherreport.db.ServiceResponseEntity
import com.assignment.weatherreport.model.ApiResponse
import com.assignment.weatherreport.model.ServiceResponse
import com.assignment.weatherreport.service.RetrofitClient
import com.assignment.weatherreport.service.Webservice

/**
 * Intermediate class between Service and local database
 *
 */
class ServiceRepository {

    var webservice: Webservice = RetrofitClient.webservice
    var webserviceImg: Webservice = RetrofitClient.webserviceImg

    /**
     * Get weather data by making service call and convert JSON body to ServiceResponse Object
     *
     * @param addressString
     * @param YOUR_API_KEY
     * @return
     */
    fun getCurrentWeatherData(addressString:String,YOUR_API_KEY:String): LiveData<ApiResponse> {
        val liveData = MutableLiveData<ApiResponse>()

        webservice.getCurrentWeatherData(addressString,YOUR_API_KEY).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonString = JSONObject(Gson().toJson(response.body()))
                    val serviceReponse = Gson().fromJson(jsonString.toString(), ServiceResponse::class.java)

                    val apiResponse = ApiResponse()
                    apiResponse.serviceResponse = serviceReponse
                    apiResponse.error = null
                    liveData.value = apiResponse

                }
                else{
                    val jObjError = JSONObject(response.errorBody()?.string())
                    val apiResponse = ApiResponse()
                    apiResponse.serviceResponse = null
                    apiResponse.error = jObjError.getString("message")
                    liveData.value = apiResponse
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                val apiResponse = ApiResponse()
                apiResponse.serviceResponse = null
                apiResponse.error = t.localizedMessage
                liveData.value = apiResponse
            }
        })

        return liveData
    }

    suspend fun insertCurrentWeatherData(serviceResponseEntity: ServiceResponseEntity) {
        MyApplication.database?.serviceResponseDao()?.deleteAll()
        MyApplication.database?.serviceResponseDao()?.insert(serviceResponseEntity)
    }

    /**
     * Get weather data from local database
     *
     * @return
     */
    fun getCurrentWeatherData():LiveData<ServiceResponseEntity>? {
        return MyApplication.database?.serviceResponseDao()?.getData()
    }

    /**
     * Get image body and convert to bitmap to display in view
     *
     * @param iconStr
     * @return
     */
    fun getCurrentWeatherImage(iconStr:String): LiveData<ApiResponse> {
        val liveData = MutableLiveData<ApiResponse>()

        webserviceImg.getCurrentWeatherImage(iconStr).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val apiResponse = ApiResponse()
                    apiResponse.serviceResponse = null
                    apiResponse.weatherImage = null
                    apiResponse.error = null
                    if (response.body() != null) {
                        val bmp = BitmapFactory.decodeStream(response.body()!!.byteStream())
                        apiResponse.weatherImage = bmp
                    }
                    liveData.value = apiResponse

                }
                else{
                    val jObjError = JSONObject(response.errorBody()?.string())
                    val apiResponse = ApiResponse()
                    apiResponse.serviceResponse = null
                    apiResponse.error = jObjError.getString("message")
                    liveData.value = apiResponse
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                val apiResponse = ApiResponse()
                apiResponse.serviceResponse = null
                apiResponse.weatherImage = null
                apiResponse.error = t.localizedMessage
                liveData.value = apiResponse
            }
        })

        return liveData
    }


}