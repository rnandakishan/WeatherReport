package com.assignment.weatherreport.model

import android.graphics.Bitmap

class ApiResponse {
    var serviceResponse: ServiceResponse? = null
    var weatherImage: Bitmap? = null
    var error: String? = null
}