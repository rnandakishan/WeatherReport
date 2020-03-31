package com.assignment.weatherreport.model

data class ServiceResponse (

    val coord: Coord?=null,
    val weather:ArrayList<Weather>?=null,
    val base: String ?= null,
    val main: main?= null,
    val visibility: String ?= null,
    val wind: wind?= null,
    val clouds: clouds?= null,
    val dt: Long = 0,
    val sys: sys?= null,
    val timezone: String ?= null,
    val id: String ?= null,
    val name: String ?= null,
    val cod: String ?= null
    )

data class sys(
    val type:String = "",
    val id:String = "",
    val country:String = "",
    val sunrise:Long = 0,
    val sunset:Long = 0
)

data class clouds(val all:String = "")

data class wind(
    val speed:String = "",
    val deg:String = ""
)

data class main(
    val temp:Float = 0F,
    val feels_like:String = "",
    val temp_min:String = "",
    val temp_max:String = "",
    val pressure:String = "",
    val humidity:String = ""
)

data class Weather(
    val id:String = "",
    val main:String = "",
    val description:String = "",
    val icon:String = "")


data class Coord(val lon:Float = 0F,
    val lat:Float = 0F)
