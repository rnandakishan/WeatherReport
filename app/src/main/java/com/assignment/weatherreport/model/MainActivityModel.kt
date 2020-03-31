package com.assignment.weatherreport.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.assignment.weatherreport.*
import com.assignment.weatherreport.db.AppDatabase
import com.assignment.weatherreport.db.ServiceResponseEntity
import com.assignment.weatherreport.repo.LocationLiveData
import com.assignment.weatherreport.repo.ServiceRepository


class MainActivityModel(application: Application): AndroidViewModel(application) {

    private val repository: ServiceRepository =
        ServiceRepository()
    init {
        MyApplication.database = AppDatabase(application)
    }

    fun getCurrentWeatherData(): LiveData<ServiceResponseEntity>? {
        return repository.getCurrentWeatherData()
    }

    fun getCurrentWeatherImage(iconStr:String): LiveData<ApiResponse> {
        return repository.getCurrentWeatherImage(iconStr)
    }

    fun getLocationData(application: Context) =
        LocationLiveData(application)
}