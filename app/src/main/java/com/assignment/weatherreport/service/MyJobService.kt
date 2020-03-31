package com.assignment.weatherreport.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.assignment.weatherreport.model.ApiResponse
import com.assignment.weatherreport.MyApplication
import com.assignment.weatherreport.repo.ServiceRepository
import com.assignment.weatherreport.db.ServiceResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Service which is triggered every 2 hours and gets weather data from server
 *
 */
class MyJobService : JobService() {
    val repository: ServiceRepository = ServiceRepository()
    var getCurrentWeatherLiveData: LiveData<ApiResponse>? = null

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    /**
     * This method gets triggered when job is started and observes the weather data after starting the service,saves it to database when data is changed.
     *
     * @param params
     * @return
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        getCurrentWeatherLiveData = getCurrentWeatherData(
            MyApplication.addressString,
            MyApplication.YOUR_API_KEY
        )
        getCurrentWeatherLiveData!!.observeForever(object : Observer<ApiResponse> {
            override fun onChanged(apiResponse: ApiResponse?) {
                if (apiResponse == null) {
                    return
                } else {
                    if (apiResponse.error != null) {
                        Toast.makeText(
                            applicationContext,
                            apiResponse.error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val serviceReponseEntity =
                            ServiceResponseEntity()
                        serviceReponseEntity.lon =
                            apiResponse.serviceResponse?.coord?.lon.toString()
                        serviceReponseEntity.lat =
                            apiResponse.serviceResponse?.coord?.lat.toString()
                        serviceReponseEntity.main =
                            apiResponse.serviceResponse?.weather?.get(0)?.main.toString()
                        serviceReponseEntity.description =
                            apiResponse.serviceResponse?.weather?.get(0)?.description.toString()
                        serviceReponseEntity.icon =
                            apiResponse.serviceResponse?.weather?.get(0)?.icon.toString()
                        serviceReponseEntity.temp =
                            apiResponse.serviceResponse?.main?.temp.toString()
                        serviceReponseEntity.feels_like =
                            apiResponse.serviceResponse?.main?.feels_like.toString()
                        serviceReponseEntity.temp_min =
                            apiResponse.serviceResponse?.main?.temp_min.toString()
                        serviceReponseEntity.temp_max =
                            apiResponse.serviceResponse?.main?.temp_max.toString()
                        serviceReponseEntity.pressure =
                            apiResponse.serviceResponse?.main?.pressure.toString()
                        serviceReponseEntity.humidity =
                            apiResponse.serviceResponse?.main?.humidity.toString()
                        serviceReponseEntity.visibility =
                            apiResponse.serviceResponse?.visibility.toString()
                        serviceReponseEntity.speed =
                            apiResponse.serviceResponse?.wind?.speed.toString()
                        serviceReponseEntity.deg = apiResponse.serviceResponse?.wind?.deg.toString()
                        serviceReponseEntity.dt = apiResponse.serviceResponse?.dt.toString()
                        serviceReponseEntity.type =
                            apiResponse.serviceResponse?.sys?.type.toString()
                        serviceReponseEntity.country =
                            apiResponse.serviceResponse?.sys?.country.toString()
                        serviceReponseEntity.sunrise =
                            apiResponse.serviceResponse?.sys?.sunrise.toString()
                        serviceReponseEntity.sunset =
                            apiResponse.serviceResponse?.sys?.sunset.toString()
                        serviceReponseEntity.timezone =
                            apiResponse.serviceResponse?.timezone.toString()
                        serviceReponseEntity.name = apiResponse.serviceResponse?.name.toString()
                        serviceReponseEntity.cod = apiResponse.serviceResponse?.cod.toString()

                        insertCurrentWeatherData(serviceReponseEntity)
                        jobFinished(params, false)
                    }
                }
                getCurrentWeatherLiveData!!.removeObserver(this)
            }
        })
        return true
    }

    /**
     * Calls method in repository class to get the data from service
     *
     * @param addressString
     * @param YOUR_API_KEY
     * @return
     */
    private fun getCurrentWeatherData(
        addressString: String,
        YOUR_API_KEY: String
    ): LiveData<ApiResponse> {
        return repository.getCurrentWeatherData(addressString, YOUR_API_KEY)
    }

    /**
     * Calls method in repository class to save the data to local database
     *
     * @param serviceResponseEntity
     */
    fun insertCurrentWeatherData(serviceResponseEntity: ServiceResponseEntity) = CoroutineScope(
        Dispatchers.Main
    ).launch {
        repository.insertCurrentWeatherData(serviceResponseEntity)
    }
}
