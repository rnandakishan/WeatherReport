package com.assignment.weatherreport.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.weatherreport.model.MainActivityModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.LiveData
import android.location.Geocoder
import com.assignment.weatherreport.repo.LocationLiveData
import com.assignment.weatherreport.repo.LocationModel
import com.assignment.weatherreport.MyApplication
import com.assignment.weatherreport.R
import com.assignment.weatherreport.db.ServiceResponseEntity

/**
 * Main activity to display the wheather report by fetching data locally as well as from server
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appViewModel: MainActivityModel
    var getLocationLiveData: LiveData<LocationModel>? = null
    var getCurrentWeatherLiveData: LiveData<ServiceResponseEntity>? = null

    /**
     * Create the view and initialize ViewModel class
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Weather Report"
        appViewModel = ViewModelProvider(this).get(MainActivityModel::class.java)
    }

    /**
     * Start to get location
     *
     */
    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    /**
     * Start a job to get wheather data from service and observe the data from local database to display in view.
     *
     */
    private fun startGettingWeatherUpdate() {
        MyApplication.scheduleJob(this)
        getCurrentWeatherLiveData?.removeObservers(this)
        getCurrentWeatherLiveData = appViewModel.getCurrentWeatherData()
        getCurrentWeatherLiveData!!.observe(this, Observer {
            if (it == null) {
                return@Observer
            } else {
                tv_humidity_value.text = "${it.humidity}%"
                tv_pressure_value.text = "${it.pressure}hPA"
                tv_visibility_value.text = "${it.visibility}m/s"
                weatherIconStr.text = it.description.split(
                    ' '
                ).joinToString(" ") { it.capitalize() }
                temperatureValue.text = (it.temp.toFloat().minus(273.15)).toInt().toString()
                locationString.text = it.name
                sunriseValueTv.text = convertLongToTime(it.sunrise.toLong(), "HH:mm a")
                sunsetValueTv.text = convertLongToTime(it.sunset.toLong(), "HH:mm a")
                tv_date_value.text = convertLongToTime(it.dt.toLong(), "EEE, d MMM HH:mm a")
                appViewModel.getCurrentWeatherImage(it.icon)
                    .observe(this, Observer {
                        if (it != null) {
                            if (it.error == null) {
                                weatherIcon.setImageBitmap(it.weatherImage)
                            }
                        }
                    })

            }
        })
    }

    /**
     * Observe location data repeatedly and then parse it using Geocoder API
     *
     */
    private fun startLocationUpdate() {
        getLocationLiveData?.removeObservers(this)
        getLocationLiveData = appViewModel.getLocationData(this)
        (getLocationLiveData as LocationLiveData).observe(this, Observer {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val cityName = addresses[0].locality
            val stateName = addresses[0].adminArea
            val pinCode = addresses[0].postalCode
            val flag = MyApplication.addressString.isEmpty()
            MyApplication.addressString = "$cityName,$stateName,$pinCode"

            if (flag)
                startGettingWeatherUpdate()

        })
        if (MyApplication.addressString.isNotEmpty())
            startGettingWeatherUpdate()
    }

    /**
     * Convert long value to time
     *
     * @param time
     * @param pattern
     * @return
     */
    fun convertLongToTime(time: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time * 1000L
        return sdf.format(calendar.time)
    }


}


