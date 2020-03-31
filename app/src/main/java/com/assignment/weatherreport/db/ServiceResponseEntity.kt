package com.assignment.weatherreport.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Creating of table with table name "ServiceResponse_table"
 *
 * @property id
 * @property lon
 * @property lat
 * @property main
 * @property description
 * @property icon
 * @property temp
 * @property feels_like
 * @property temp_min
 * @property temp_max
 * @property pressure
 * @property humidity
 * @property visibility
 * @property speed
 * @property deg
 * @property dt
 * @property type
 * @property country
 * @property sunrise
 * @property sunset
 * @property timezone
 * @property name
 * @property cod
 */
@Entity(tableName = "ServiceResponse_table")
data class ServiceResponseEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "lon") var lon: String,
    @ColumnInfo(name = "lat") var lat: String,
    @ColumnInfo(name = "main") var main: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "icon") var icon: String,
    @ColumnInfo(name = "temp") var temp: String,
    @ColumnInfo(name = "feels_like") var feels_like: String,
    @ColumnInfo(name = "temp_min") var temp_min: String,
    @ColumnInfo(name = "temp_max") var temp_max: String,
    @ColumnInfo(name = "pressure") var pressure: String,
    @ColumnInfo(name = "humidity") var humidity: String,
    @ColumnInfo(name = "visibility") var visibility: String,
    @ColumnInfo(name = "speed") var speed: String,
    @ColumnInfo(name = "deg") var deg: String,
    @ColumnInfo(name = "dt") var dt: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "sunrise") var sunrise: String,
    @ColumnInfo(name = "sunset") var sunset: String,
    @ColumnInfo(name = "timezone") var timezone: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "cod") var cod: String
) {
    constructor() : this(
        0, "", "", "", "", "", "", ""
        , "", "", "", ""
        , "", "", "", ""
        , "", "", "", ""
        , "", "", ""
    )
}
