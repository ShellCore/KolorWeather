package com.shellcore.android.kolorweather.models

import com.shellcore.android.kolorweather.R

/**
 * Created by MOGC on 22/01/2018.
 */
data class CurrentWeather (var icon : String,
                      var summary : String,
                      var temp : Double,
                      var precip : Double) {

    fun getIconResource() : Int {
        when (icon) {
            "clear-day"           -> return R.drawable.clear_day
            "clear-night"         -> return R.drawable.clear_night
            "cloudy"              -> return R.drawable.cloudy
            "partly-cloudy-day"   -> return R.drawable.partly_cloudy_day
            "partly-cloudy-night" -> return R.drawable.partly_cloudy_night
            "rain"                -> return R.drawable.rain
            "snow"                -> return R.drawable.snow
            "wind"                -> return R.drawable.wind
            else                  -> return R.drawable.crash
        }
    }
}